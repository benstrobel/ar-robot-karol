package com.example.robotkarolar.karollogic_ben.instructions;

import com.example.robotkarolar.karollogic_ben.instructions.visitors.InstructionVisitor;

public abstract class Instruction {
    public abstract void accept(InstructionVisitor visitor);
}
