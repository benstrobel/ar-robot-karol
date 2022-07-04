package com.example.robotkarolar.karollogic.instructions.statements;

import com.example.robotkarolar.karollogic.instructions.Instruction;
import com.example.robotkarolar.karollogic.instructions.visitors.InstructionVisitor;

public class Noop extends Instruction {
    @Override
    public void accept(InstructionVisitor visitor) {
        visitor.accept(this);
    }
}
