package com.example.robotkarolar.karollogic.instructions.controlflow;

import com.example.robotkarolar.karollogic.instructions.visitors.InstructionVisitor;
import com.example.robotkarolar.karollogic.instructions.expressions.Expression;

public class While extends ControlFlow {

    private Expression condition;
    private final CodeBlock codeBlock;

    public While(Expression condition, CodeBlock codeBlock) {
        this.condition = condition;
        this.codeBlock = codeBlock;
    }

    public void setCondition(Expression condition) {
        this.condition = condition;
    }

    public Expression getCondition() {
        return condition;
    }

    public CodeBlock getCodeBlock() {
        return codeBlock;
    }

    @Override
    public void accept(InstructionVisitor visitor) {
        visitor.accept(this);
    }
}
