package com.example.robotkarolar.karollogic_ben.instructions.controlflow;

import com.example.robotkarolar.karollogic_ben.instructions.Instruction;
import com.example.robotkarolar.karollogic_ben.instructions.visitors.InstructionVisitor;
import com.example.robotkarolar.karollogic_ben.instructions.expressions.Expression;

public class If extends Instruction {

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
