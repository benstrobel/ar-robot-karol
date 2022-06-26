package com.example.robotkarolar.karollogic_ben.instructions.expressions;

import com.example.robotkarolar.karollogic_ben.instructions.Instruction;

public abstract class Expression extends Instruction {
    public abstract boolean accept(ExpressionVisitor visitor);
}
