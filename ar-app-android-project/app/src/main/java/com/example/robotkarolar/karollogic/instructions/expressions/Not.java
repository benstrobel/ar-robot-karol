package com.example.robotkarolar.karollogic.instructions.expressions;

import com.example.robotkarolar.karollogic.instructions.visitors.InstructionVisitor;

public class Not extends Expression {

    private final Expression child;

    public Not(Expression child) {
        this.child = child;
    }

    public Expression getChild() {
        return child;
    }

    @Override
    public void accept(InstructionVisitor visitor) {
        visitor.accept(this);
    }

    @Override
    public boolean accept(ExpressionVisitor visitor) {
        return visitor.accept(this);
    }
}
