package com.example.robotkarolar.karollogic.instructions.controlflow

import com.example.robotkarolar.karollogic.instructions.Instruction
import com.example.robotkarolar.karollogic.instructions.controlflow.ControlFlow
import com.example.robotkarolar.karollogic.instructions.controlflow.While
import com.example.robotkarolar.karollogic.instructions.controlflow.CodeBlock
import com.example.robotkarolar.karollogic.instructions.visitors.InstructionVisitor
import java.util.*

class CodeBlock : ControlFlow {
    private var instructions: MutableList<Instruction>

    constructor() {
        instructions = ArrayList()
    }

    constructor(instructions: Array<Instruction>?) {
        this.instructions = ArrayList()
        Arrays.stream(instructions)
            .forEach { instruction: Instruction -> this.addInstruction(instruction) }
    }

    fun getInstructions(): Array<Instruction> {
        return instructions.toArray<Instruction>(arrayOf<Instruction>())
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

    fun getNextAfter(instruction: Instruction): Instruction? {
        val foundInstructionIndex = instructions.indexOf(instruction)
        if (foundInstructionIndex == -1 || instructions.size - 1 < foundInstructionIndex + 1) {
            return if (parent == null) {
                null
            } else {
                parent
            }
        }
        val next = instructions[foundInstructionIndex + 1]
        return if (next is ControlFlow) {
            if (next is If) {
                val condition =
                    next.condition
            } else if (next is While) {
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

    fun getPreviousBefore(instruction: Instruction): Instruction? {
        return getPreviousBefore(instruction, false)
    }

    fun getPreviousBefore(instruction: Instruction, hasSteppedOut: Boolean): Instruction? {
        if (instruction is ControlFlow && !hasSteppedOut) {
            val cb = instruction.codeBlock
            return cb.instructions[cb.size() - 1]
        }
        val foundInstructionIndex = instructions.indexOf(instruction)
        return if (foundInstructionIndex == -1 || 0 > foundInstructionIndex - 1) {
            if (parent == null) {
                null
            } else {
                (parent.parent as ControlFlow).codeBlock.getPreviousBefore(parent, true)
            }
        } else instructions[foundInstructionIndex - 1]
    }

    fun size(): Int {
        return instructions.size
    }

    override fun accept(visitor: InstructionVisitor) {
        visitor.accept(this)
    }

    override fun getCodeBlock(): CodeBlock {
        return this
    }
}