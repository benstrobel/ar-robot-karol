package com.example.robotkarolar.karollogic.instructions

import android.os.Parcelable
import com.example.robotkarolar.karollogic.instructions.controlflow.ControlFlow
import com.example.robotkarolar.karollogic.instructions.expressions.And
import com.example.robotkarolar.karollogic.instructions.expressions.EmptyExpression
import com.example.robotkarolar.karollogic.instructions.expressions.Not
import com.example.robotkarolar.karollogic.instructions.expressions.Or
import com.example.robotkarolar.karollogic.instructions.visitors.InstructionVisitor

/**
 * Executable in the programm code needs to confirm to this class
 */

abstract class Instruction: Parcelable {
    val id: Long
    var parent: Instruction? = null

    abstract fun accept(visitor: InstructionVisitor?)

    fun delete(): Boolean {
        if (parent == null) return false
        if (parent is ControlFlow) {
            (parent as ControlFlow).codeBlock.removeInstruction(this)
        } else if (parent is Not) {
            (parent as Not).child = EmptyExpression()
        } else if (parent is And) {
            val and = parent as And
            if (and.left === this) {
                and.left = EmptyExpression()
            } else if (and.right === this) {
                and.right = EmptyExpression()
            } else {
                return false
            }
        } else if (parent is Or) {
            val or = parent as Or
            if (or.left === this) {
                or.left = EmptyExpression()
            } else if (or.right === this) {
                or.right = EmptyExpression()
            } else {
                return false
            }
        }
        return true
    }

    companion object {
        private var nextId: Long = 0
    }

    init {
        id = nextId++
    }
}