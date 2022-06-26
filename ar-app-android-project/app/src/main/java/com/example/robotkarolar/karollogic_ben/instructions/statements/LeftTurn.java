package com.example.robotkarolar.karollogic_ben.instructions.statements;

import com.example.robotkarolar.karollogic_ben.instructions.Instruction;
import com.example.robotkarolar.karollogic_ben.instructions.visitors.InstructionVisitor;

public class LeftTurn extends Instruction {
    @Override
    public void accept(InstructionVisitor visitor) {
        visitor.accept(this);
    }
}
