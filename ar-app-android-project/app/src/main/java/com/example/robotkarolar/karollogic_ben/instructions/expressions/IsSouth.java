package com.example.robotkarolar.karollogic_ben.instructions.expressions;

import com.example.robotkarolar.karollogic_ben.instructions.visitors.InstructionVisitor;

public class IsSouth extends Expression {
    @Override
    public void accept(InstructionVisitor visitor) {
        visitor.accept(this);
    }

    @Override
    public boolean accept(ExpressionVisitor visitor) {
        return visitor.accept(this);
    }
}
