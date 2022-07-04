package com.example.robotkarolar.karollogic.instructions;

import com.example.robotkarolar.karollogic.instructions.controlflow.ControlFlow;
import com.example.robotkarolar.karollogic.instructions.expressions.And;
import com.example.robotkarolar.karollogic.instructions.expressions.EmptyExpression;
import com.example.robotkarolar.karollogic.instructions.expressions.Not;
import com.example.robotkarolar.karollogic.instructions.expressions.Or;
import com.example.robotkarolar.karollogic.instructions.visitors.InstructionVisitor;

public abstract class Instruction {

    private static long nextId = 0;
    private final long id;

    public Instruction() {
        id = nextId++;
    }

    private Instruction parent;

    public void setParent(Instruction parent) {
        this.parent = parent;
    }

    public Instruction getParent() {
        return parent;
    }

    public abstract void accept(InstructionVisitor visitor);

    public long getId() {
        return id;
    }

    public boolean delete() {
        if(parent == null) return false;

        if(parent instanceof ControlFlow) {
            ((ControlFlow)parent).getCodeBlock().removeInstruction(this);
        } else if (parent instanceof Not) {
            ((Not) parent).setChild(new EmptyExpression());
        } else if (parent instanceof And) {
            And and = ((And)parent);
            if(and.getLeft() == this) {
                and.setLeft(new EmptyExpression());
            } else if (and.getRight() == this) {
                and.setRight(new EmptyExpression());
            } else {
                return false;
            }
        } else if (parent instanceof Or) {
            Or or = ((Or)parent);
            if(or.getLeft() == this) {
                or.setLeft(new EmptyExpression());
            } else if (or.getRight() == this) {
                or.setRight(new EmptyExpression());
            } else {
                return false;
            }
        }

        return true;
    }
}
