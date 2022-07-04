package com.example.robotkarolar.karollogic.instructions.expressions;

import com.example.robotkarolar.karollogic.instructions.visitors.InstructionVisitor;

public class IsEast extends Expression {
    @Override
    public void accept(InstructionVisitor visitor) {
        visitor.accept(this);
    }

    @Override
    public boolean accept(ExpressionVisitor visitor) {
        return visitor.accept(this);
    }
}
