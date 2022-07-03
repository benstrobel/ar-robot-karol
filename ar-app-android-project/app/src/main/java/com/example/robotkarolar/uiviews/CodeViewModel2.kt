package com.example.robotkarolar.uiviews

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.robotkarolar.karollogic_ben.instructions.Instruction
import com.example.robotkarolar.karollogic_ben.instructions.controlflow.CodeBlock
import com.example.robotkarolar.karollogic_ben.instructions.controlflow.If
import com.example.robotkarolar.karollogic_ben.instructions.controlflow.While

class CodeViewModel2: ViewModel(){
    val codeBlock: MutableState<Instruction> = mutableStateOf(CodeBlock())
    var cursor: Instruction = codeBlock.value

    fun addInstruction(instruction: Instruction, currentCursor: Instruction = cursor, afterChild: Instruction?) {
        synchronized(currentCursor) { // Prevents race conditions by spamming instruction adds
            if(afterChild != null) {
                when(currentCursor) {
                    is CodeBlock -> {
                        currentCursor.addInstruction(instruction, afterChild)
                        cursor = instruction
                        cursor.parent = currentCursor
                    }
                    is If -> {
                        currentCursor.codeBlock.addInstruction(instruction, afterChild)
                        cursor = instruction
                        cursor.parent = currentCursor
                    }
                    is While -> {
                        currentCursor.codeBlock.addInstruction(instruction, afterChild)
                        cursor = instruction
                        cursor.parent = currentCursor
                    }
                }
            } else {
                if(currentCursor.parent == null) { // The only element without parent is the root codeBlock
                    (currentCursor as CodeBlock).addInstruction(instruction)
                    cursor = instruction
                    cursor.parent = currentCursor
                } else { // The cursor always points to the element after which the next instruction should be inserted (but in the same scope as the element pointed to)
                    addInstruction(instruction, currentCursor.parent, currentCursor)
                }
            }
        }
    }

    fun next(currentCursor: Instruction = cursor, nextFrom: Instruction? = null) {
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

        cursor = codeBlock!!.getNextAfter(nextFrom)
    }

    fun previous(currentCursor: Instruction = cursor, previousFrom: Instruction? = null) {
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
                next(currentCursor.parent, currentCursor)
            }
            return
        }

        cursor = codeBlock!!.getPreviousBefore(previousFrom)
    }

    fun clear() {
        codeBlock.value = CodeBlock()
        cursor = codeBlock.value
    }
}