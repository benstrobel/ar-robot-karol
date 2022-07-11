package com.example.robotkarolar.karollogic.instructions.expressions

import com.example.robotkarolar.karollogic.instructions.visitors.InstructionVisitor
import kotlinx.parcelize.Parcelize

@Parcelize
class And(var left: Expression, var right: Expression) : Expression() {
    override fun accept(visitor: InstructionVisitor?) {
        visitor!!.accept(this)
    }

    override fun accept(visitor: ExpressionVisitor): Boolean {
        return visitor.accept(this)
    }
}