package com.example.robotkarolar.karollogic.instructions;

import com.example.robotkarolar.karollogic.instructions.controlflow.ControlFlow;
import com.example.robotkarolar.karollogic.instructions.visitors.InstructionVisitor;

public abstract class Instruction {

    private static long nextId = 0;
    private final long id;

    public Instruction() {
        id = nextId++;
    }

    private ControlFlow parent;

    public void setParent(ControlFlow parent) {
        this.parent = parent;
    }

    public ControlFlow getParent() {
        return parent;
    }

    public abstract void accept(InstructionVisitor visitor);

    public long getId() {
        return id;
    }

    public boolean delete() {
        if(parent == null) return false;
        parent.getCodeBlock().removeInstruction(this);
        return true;
    }
}
