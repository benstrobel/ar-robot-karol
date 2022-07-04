package com.example.robotkarolar.uiviews

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.robotkarolar.karollogic.instructions.Instruction
import com.example.robotkarolar.karollogic.instructions.controlflow.CodeBlock
import com.example.robotkarolar.karollogic.instructions.controlflow.ControlFlow
import com.example.robotkarolar.karollogic.instructions.controlflow.If
import com.example.robotkarolar.karollogic.instructions.controlflow.While
import com.example.robotkarolar.karollogic.instructions.expressions.*
import com.example.robotkarolar.karollogic.instructions.statements.Noop
import kotlin.math.exp

class CodeViewModel: ViewModel(){
    val first = Noop()
    val root = CodeBlock(arrayOf(first))
    val codeBlock: MutableState<Instruction> = mutableStateOf(root)
    var cursor: MutableState<Instruction> = mutableStateOf(first)

    fun addInstruction(instruction: Instruction, currentCursor: Instruction = cursor.value, afterChild: Instruction? = null) {
        synchronized(currentCursor) { // Prevents race conditions by spamming instruction adds
            if(afterChild != null) {
                when(currentCursor) {
                    is CodeBlock -> {
                        currentCursor.addInstruction(instruction, afterChild)
                        cursor.value = instruction
                    }
                    is If -> {
                        currentCursor.codeBlock.addInstruction(instruction, afterChild)
                        cursor.value = instruction
                    }
                    is While -> {
                        currentCursor.codeBlock.addInstruction(instruction, afterChild)
                        cursor.value = instruction
                    }
                }
            } else {
                if(currentCursor is EmptyExpression && instruction is Expression) {
                    val parent = currentCursor.parent
                    when(parent) {
                        is If -> {
                            parent.condition = instruction
                            next()
                        }
                        is While -> {
                            parent.condition = instruction
                            next()
                        }
                        is Not -> {
                            parent.child = instruction
                            next()
                        }
                        is And -> {
                            if(parent.left == currentCursor) {
                                parent.left = instruction
                            } else {
                                parent.right = instruction
                            }
                            next()
                        }
                        is Or -> {
                            if(parent.left == currentCursor) {
                                parent.left = instruction
                            } else {
                                parent.right = instruction
                            }
                            next()
                        }
                    }
                }

                if(currentCursor.parent == null) { // The only element without parent is the root codeBlock
                    (currentCursor as CodeBlock).addInstruction(instruction)
                    cursor.value = instruction
                } else { // The cursor always points to the element after which the next instruction should be inserted (but in the same scope as the element pointed to)
                    addInstruction(instruction, currentCursor.parent, currentCursor)
                    return
                }
            }

            when(instruction) {
                is If -> {
                    cursor.value = instruction.condition
                }
                is While -> {
                    cursor.value = instruction.condition
                }
            }
        }
    }

    private fun nextEmptyExpressionDown(expression: Expression): Expression? {
        when(expression) {
            is Not -> {
                return nextEmptyExpressionDown(expression.child)
            }
            is And -> {
                val expr = nextEmptyExpressionDown(expression.left)
                if(expr != null) return expr;
                return nextEmptyExpressionDown(expression.right)
            }
            is Or -> {
                val expr = nextEmptyExpressionDown(expression.left)
                if(expr != null) return expr;
                return nextEmptyExpressionDown(expression.right)
            }
            is EmptyExpression -> {
                return expression
            }
            else -> {
                return null
            }
        }
    }

    private fun nextEmptyExpressionOrParentInstruction(expression: Expression): Instruction {
        if(expression.parent is Expression) {
            when(expression.parent) {
                is Not -> {
                    return nextEmptyExpressionOrParentInstruction(expression.parent as Expression)
                }
                is And -> {
                    if((expression.parent as And).left == expression) {
                        val expr = nextEmptyExpressionDown(expression)
                        if(expr != null) return expr
                    }
                    return nextEmptyExpressionOrParentInstruction(expression.parent as Expression)
                }
                is Or -> {
                    if((expression.parent as Or).left == expression) {
                        val expr = nextEmptyExpressionDown(expression)
                        if(expr != null) return expr
                    }
                    return nextEmptyExpressionOrParentInstruction(expression.parent as Expression)
                }
            }
        }
        return (expression.parent as ControlFlow).codeBlock.instructions[0]
    }

    fun next(currentCursor: Instruction = cursor.value, nextFrom: Instruction? = null) {
        var codeBlock: CodeBlock? = null

        if(nextFrom != null) {
            when (currentCursor) {
                is CodeBlock -> {
                    codeBlock = currentCursor
                }
                is If -> {
                    codeBlock = currentCursor.codeBlock
                }
                is While -> {
                    codeBlock = currentCursor.codeBlock
                }
                else -> {
                    throw Exception("Invariant broken, Instructions must always have control flow or \"not/or/and\" as parents")
                }
            }

            val next = codeBlock!!.getNextAfter(nextFrom)
            if(next != null) cursor.value = next
            return
        }

        if(currentCursor.parent != null) {
            if(currentCursor is Expression) {
                cursor.value = nextEmptyExpressionOrParentInstruction(currentCursor)
            } else {
                next(currentCursor.parent, currentCursor)
            }
        }
    }

    private fun previousEmptyExpressionDown(expression: Expression): Expression? {
        when(expression) {
            is Not -> {
                return previousEmptyExpressionDown(expression.child)
            }
            is And -> {
                val expr = previousEmptyExpressionDown(expression.right)
                if(expr != null) return expr;
                return previousEmptyExpressionDown(expression.left)
            }
            is Or -> {
                val expr = previousEmptyExpressionDown(expression.right)
                if(expr != null) return expr;
                return previousEmptyExpressionDown(expression.left)
            }
            is EmptyExpression -> {
                return expression
            }
            else -> {
                return null
            }
        }
    }

    private fun previousEmptyExpressionOrParentInstruction(expression: Expression): Instruction {
        if(expression.parent is Expression) {
            when(expression.parent) {
                is Not -> {
                    return previousEmptyExpressionOrParentInstruction(expression.parent as Expression)
                }
                is And -> {
                    if((expression.parent as And).right == expression) {
                        val expr = previousEmptyExpressionDown(expression)
                        if(expr != null) return expr
                    }
                    return nextEmptyExpressionOrParentInstruction(expression.parent as Expression)
                }
                is Or -> {
                    if((expression.parent as Or).right == expression) {
                        val expr = previousEmptyExpressionDown(expression)
                        if(expr != null) return expr
                    }
                    return nextEmptyExpressionOrParentInstruction(expression.parent as Expression)
                }
            }
        }
        return (expression.parent as ControlFlow).codeBlock.instructions[0]
    }

    fun previous(currentCursor: Instruction = cursor.value, previousFrom: Instruction? = null) {
        var codeBlock: CodeBlock? = null

        if(previousFrom != null) {
            when (currentCursor) {
                is CodeBlock -> {
                    codeBlock = currentCursor
                }
                is If -> {
                    codeBlock = currentCursor.codeBlock
                }
                is While -> {
                    codeBlock = currentCursor.codeBlock
                }
                else -> {
                    throw Exception("Invariant broken, Instructions must always have control flow or \"not/or/and\" as parents")
                }
            }

            val previous = codeBlock!!.getPreviousBefore(previousFrom)
            if(previous != null)  cursor.value = previous
            return
        }

        if(currentCursor.parent != null) {
            if(currentCursor is Expression) {
                cursor.value = previousEmptyExpressionOrParentInstruction(currentCursor)
            } else {
                previous(currentCursor.parent, currentCursor)
            }
        }
    }

    fun clear() {
        codeBlock.value = CodeBlock()
        cursor.value = codeBlock.value
    }
}