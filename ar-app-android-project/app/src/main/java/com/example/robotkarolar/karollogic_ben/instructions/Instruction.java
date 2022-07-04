package com.example.robotkarolar.karollogic_ben.instructions;

import com.example.robotkarolar.karollogic_ben.instructions.controlflow.CodeBlock;
import com.example.robotkarolar.karollogic_ben.instructions.controlflow.ControlFlow;
import com.example.robotkarolar.karollogic_ben.instructions.controlflow.If;
import com.example.robotkarolar.karollogic_ben.instructions.controlflow.While;
import com.example.robotkarolar.karollogic_ben.instructions.visitors.InstructionVisitor;

public abstract class Instruction {

    private ControlFlow parent;

    public void setParent(ControlFlow parent) {
        this.parent = parent;
    }

    public ControlFlow getParent() {
        return parent;
    }

    public abstract void accept(InstructionVisitor visitor);

    public boolean delete() {
        if(parent == null) return false;
        parent.getCodeBlock().removeInstruction(this);
        return true;
    }
}
