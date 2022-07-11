package com.example.robotkarolar.karollogic.instructions.expressions

import com.example.robotkarolar.karollogic.instructions.expressions.ExpressionVisitor
import com.example.robotkarolar.karollogic.instructions.visitors.InstructionVisitor
import kotlinx.parcelize.Parcelize

@Parcelize
class IsBorder : Expression() {
    override fun accept(visitor: InstructionVisitor?) {
        visitor!!.accept(this)
    }

    override fun accept(visitor: ExpressionVisitor): Boolean {
        return visitor.accept(this)
    }
}