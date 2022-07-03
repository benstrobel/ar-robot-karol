package com.example.robotkarolar.karollogic_ben.instructions.controlflow;

import com.example.robotkarolar.karollogic_ben.instructions.Instruction;
import com.example.robotkarolar.karollogic_ben.instructions.visitors.InstructionVisitor;
import com.example.robotkarolar.karollogic_ben.instructions.expressions.Expression;

public class While extends Instruction {

    private final Expression condition;
    private final CodeBlock codeBlock;

    public While(Expression condition, CodeBlock codeBlock) {
        this.condition = condition;
        this.codeBlock = codeBlock;
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
