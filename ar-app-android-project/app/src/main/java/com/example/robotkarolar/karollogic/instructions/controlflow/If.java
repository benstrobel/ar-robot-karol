package com.example.robotkarolar.karollogic.instructions.controlflow;

import com.example.robotkarolar.karollogic.instructions.visitors.InstructionVisitor;
import com.example.robotkarolar.karollogic.instructions.expressions.Expression;

public class If extends ControlFlow {

    private final Expression condition;
    private final CodeBlock codeblock;

    public If(Expression condition, CodeBlock codeblock) {
        this.condition = condition;
        this.codeblock = codeblock;
    }

    public Expression getCondition() {
        return condition;
    }

    public CodeBlock getCodeBlock() {
        return codeblock;
    }

    @Override
    public void accept(InstructionVisitor visitor) {
        visitor.accept(this);
    }
}