package com.example.robotkarolar.uiviews

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.robotkarolar.karollogic_ben.instructions.Instruction
import com.example.robotkarolar.karollogic_ben.instructions.controlflow.CodeBlock
import com.example.robotkarolar.karollogic_ben.instructions.controlflow.If
import com.example.robotkarolar.karollogic_ben.instructions.controlflow.While
import com.example.robotkarolar.karollogic_ben.instructions.statements.Noop

class CodeViewModel2: ViewModel(){
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
                    cursor.value = instruction.codeBlock.instructions[0]
                }
                is While -> {
                    cursor.value = instruction.codeBlock.instructions[0]
                }
            }
        }
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
                    throw Exception("Invariant broken, Instructions must always have control flow as parents")
                }
            }
        } else {
            if(currentCursor.parent != null) {
                next(currentCursor.parent, currentCursor)
            }
            return
        }

        val next = codeBlock!!.getNextAfter(nextFrom)
        if(next != null) cursor.value = next
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
                    throw Exception("Invariant broken, Instructions must always have control flow as parents")
                }
            }
        } else {
            if(currentCursor.parent != null) {
                previous(currentCursor.parent, currentCursor)
            }
            return
        }

        val previous = codeBlock!!.getPreviousBefore(previousFrom)
        if(previous != null)  cursor.value = previous
    }

    fun clear() {
        codeBlock.value = CodeBlock()
        cursor.value = codeBlock.value
    }
}