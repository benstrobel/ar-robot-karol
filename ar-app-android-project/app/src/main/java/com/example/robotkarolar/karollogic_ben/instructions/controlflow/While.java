package com.example.robotkarolar.karollogic_ben.instructions.controlflow;

import com.example.robotkarolar.karollogic_ben.instructions.Instruction;
import com.example.robotkarolar.karollogic_ben.instructions.visitors.InstructionVisitor;
import com.example.robotkarolar.karollogic_ben.instructions.expressions.Expression;

public class While extends Instruction {

    private final Expression condition;
    private final Instruction[] instructions;

    public While(Expression condition, Instruction[] instructions) {
        this.condition = condition;
        this.instructions = instructions;
    }

    public Expression getCondition() {
        return condition;
    }

    public Instruction[] getInstructions() {
        return instructions;
    }

    @Override
    public void accept(InstructionVisitor visitor) {
        visitor.accept(this);
    }
}
