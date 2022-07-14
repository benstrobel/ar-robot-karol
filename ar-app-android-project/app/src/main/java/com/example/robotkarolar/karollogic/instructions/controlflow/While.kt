package com.example.robotkarolar.karollogic.instructions.controlflow

import com.example.robotkarolar.karollogic.instructions.expressions.Expression
import com.example.robotkarolar.karollogic.instructions.visitors.InstructionVisitor
import kotlinx.parcelize.Parcelize

@Parcelize
class While(var condition: Expression, override val codeBlock: CodeBlock) : ControlFlow() {
    init {
        codeBlock.parent = this
        condition.parent = this
    }

    override fun accept(visitor: InstructionVisitor?) {
        visitor!!.accept(this)
    }
}