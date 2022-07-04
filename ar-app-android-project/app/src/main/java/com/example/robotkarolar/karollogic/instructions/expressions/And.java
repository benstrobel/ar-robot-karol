package com.example.robotkarolar.karollogic.instructions.expressions;

import com.example.robotkarolar.karollogic.instructions.visitors.InstructionVisitor;

public class And extends Expression {

    private final Expression left;
    private final Expression right;

    public And(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
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
