package com.example.robotkarolar.karollogic_ben.instructions;

import com.example.robotkarolar.karollogic_ben.instructions.visitors.InstructionVisitor;

public abstract class Instruction {

    private Instruction parent;

    public void setParent(Instruction parent) {
        this.parent = parent;
    }

    public Instruction getParent() {
        return parent;
    }

    public abstract void accept(InstructionVisitor visitor);
}
