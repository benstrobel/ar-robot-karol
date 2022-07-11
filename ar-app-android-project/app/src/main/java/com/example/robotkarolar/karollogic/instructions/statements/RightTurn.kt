package com.example.robotkarolar.karollogic.instructions.statements

import com.example.robotkarolar.karollogic.instructions.Instruction
import com.example.robotkarolar.karollogic.instructions.visitors.InstructionVisitor
import kotlinx.parcelize.Parcelize

@Parcelize
class RightTurn : Instruction() {
    override fun accept(visitor: InstructionVisitor?) {
        visitor!!.accept(this)
    }
}