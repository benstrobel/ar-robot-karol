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

class CodeViewModel(codeBlock: CodeBlock? = null): ViewModel(){
    val first = Noop()
    val root = codeBlock ?: CodeBlock(arrayOf(first))
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
                            instruction.parent = parent
                            cursor.value = instruction
                        }
                        is While -> {
                            parent.condition = instruction
                            instruction.parent = parent
                            cursor.value = instruction
                        }
                        is Not -> {
                            parent.child = instruction
                            instruction.parent = parent
                            cursor.value = instruction
                        }
                        is And -> {
                            if(parent.left == currentCursor) {
                                parent.left = instruction
                                instruction.parent = parent
                                cursor.value = instruction
                            } else {
                                parent.right = instruction
                                instruction.parent = parent
                                cursor.value = instruction
                            }
                        }
                        is Or -> {
                            if(parent.left == currentCursor) {
                                parent.left = instruction
                                instruction.parent = parent
                                cursor.value = instruction
                            } else {
                                parent.right = instruction
                                instruction.parent = parent
                                cursor.value = instruction
                            }
                        }
                    }
                    when(instruction) {
                        is Not -> {
                            cursor.value = instruction.child
                        }
                        is And -> {
                            cursor.value = instruction.left
                        }
                        is Or -> {
                            cursor.value = instruction.left
                        }
                        else -> {
                            next()
                        }
                    }
                } else {
                    if(currentCursor.parent == null) { // The only element without parent is the root codeBlock
                        (currentCursor as CodeBlock).addInstruction(instruction)
                        cursor.value = instruction
                    } else { // The cursor always points to the element after which the next instruction should be inserted (but in the same scope as the element pointed to)
                        addInstruction(instruction, currentCursor.parent, currentCursor)
                        return
                    }
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

            val next = getNextAfter(codeBlock!!, nextFrom)
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
        return (expression.parent as ControlFlow)
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

            if(codeBlock!!.parent != null) {
                if(codeBlock.parent is If) {
                    if(previousFrom == codeBlock.instructions[0]) {
                        val expr = previousEmptyExpressionDown((codeBlock.parent as If).condition)
                        if (expr != null){
                            cursor.value = expr
                            return
                        }
                    }
                }
                if(codeBlock.parent is While) {
                    if(previousFrom == codeBlock.instructions[0]) {
                        val expr = previousEmptyExpressionDown((codeBlock.parent as While).condition)
                        if (expr != null){
                            cursor.value = expr
                            return
                        }
                    }
                }
            }

            val previous = getPreviousBefore(codeBlock, previousFrom)
            if(previous != null)  cursor.value = previous
            return
        }

        if(currentCursor.parent != null) {
            if(currentCursor is Expression) {
                val previous = previousEmptyExpressionOrParentInstruction(currentCursor)
                if(previous is Expression) {
                    cursor.value = previous
                } else {
                    val p = getPreviousBefore((previous as ControlFlow).codeBlock,(previous.parent as ControlFlow).codeBlock.instructions[0])
                    if (p != null) cursor.value = p
                }
            } else {
                previous(currentCursor.parent, currentCursor)
            }
        }
    }

    fun clear() {
        codeBlock.value = CodeBlock()
        cursor.value = codeBlock.value
    }

    private fun getNextAfter(codeBlock: CodeBlock, instruction: Instruction): Instruction? {
        val foundInstructionIndex = codeBlock.instructions.indexOf(instruction)
        if (foundInstructionIndex == -1 || codeBlock.instructions.size - 1 < foundInstructionIndex + 1) {
            return if (codeBlock.parent == null) {
                null
            } else {
                codeBlock.parent
            }
        }
        val next = codeBlock.instructions[foundInstructionIndex + 1]
        return if (next is ControlFlow) {
            if (next is If) {
                val nextEmptyExpression = nextEmptyExpressionDown(next.condition)
                if(nextEmptyExpression != null) return nextEmptyExpression
            } else if (next is While) {
                val nextEmptyExpression = nextEmptyExpressionDown(next.condition)
                if(nextEmptyExpression != null) return nextEmptyExpression
            }
            val cb = next.codeBlock
            if (cb.size() > 0) {
                cb.instructions[0]
            } else {
                next
            }
        } else {
            next
        }
    }

    fun getPreviousBefore(codeBlock: CodeBlock, instruction: Instruction, hasSteppedOut: Boolean = false): Instruction? {
        if (instruction is ControlFlow && !hasSteppedOut) {
            val cb = instruction.codeBlock
            return cb.instructions[cb.size() - 1]
        }
        val foundInstructionIndex = codeBlock.instructions.indexOf(instruction)
        return if (foundInstructionIndex == -1 || 0 > foundInstructionIndex - 1) {
            if (codeBlock.parent == null) {
                null
            } else {
                getPreviousBefore((codeBlock.parent.parent as ControlFlow).codeBlock, codeBlock.parent, true)
            }
        } else codeBlock.instructions[foundInstructionIndex - 1]
    }
}