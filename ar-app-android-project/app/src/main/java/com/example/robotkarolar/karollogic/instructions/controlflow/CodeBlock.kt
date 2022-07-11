package com.example.robotkarolar.karollogic.instructions.controlflow

import com.example.robotkarolar.karollogic.instructions.Instruction
import com.example.robotkarolar.karollogic.instructions.visitors.InstructionVisitor
import kotlinx.parcelize.Parcelize

@Parcelize
class CodeBlock constructor(var instructions: MutableList<Instruction>, var pointer: Int = 0) : ControlFlow() {

    init {
        instructions.forEach { instruction: Instruction -> instruction.parent = this }
    }

    fun addInstruction(instruction: Instruction) {
        instructions.add(instruction)
        instruction.parent = this
    }

    fun addInstruction(instruction: Instruction, afterInstruction: Instruction) {
        val foundInstructionIndex = instructions.indexOf(afterInstruction)
        instructions.add(foundInstructionIndex + 1, instruction)
        instruction.parent = this
    }

    fun removeInstruction(instruction: Instruction) {
        instructions.remove(instruction)
    }

    fun size(): Int {
        return instructions.size
    }

    fun resetPointer() {
        pointer = 0
    }

    fun incrementPointer() {
        pointer++
    }

    override fun accept(visitor: InstructionVisitor?) {
        visitor!!.accept(this)
    }

    override val codeBlock: CodeBlock
        get() = this
}