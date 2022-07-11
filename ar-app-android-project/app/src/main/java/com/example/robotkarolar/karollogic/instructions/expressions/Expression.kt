package com.example.robotkarolar.karollogic.instructions.expressions

import com.example.robotkarolar.karollogic.instructions.Instruction
import com.example.robotkarolar.karollogic.instructions.expressions.ExpressionVisitor

abstract class Expression : Instruction() {
    abstract fun accept(visitor: ExpressionVisitor): Boolean
}