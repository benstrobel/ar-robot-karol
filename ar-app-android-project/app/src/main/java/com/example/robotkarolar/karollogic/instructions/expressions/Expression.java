package com.example.robotkarolar.karollogic.instructions.expressions;

import com.example.robotkarolar.karollogic.instructions.Instruction;

public abstract class Expression extends Instruction {
    public abstract boolean accept(ExpressionVisitor visitor);
}
