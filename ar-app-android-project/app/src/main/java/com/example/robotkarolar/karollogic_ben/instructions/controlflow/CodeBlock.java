package com.example.robotkarolar.karollogic_ben.instructions.controlflow;

import com.example.robotkarolar.karollogic_ben.instructions.Instruction;

public class CodeBlock {

    private Instruction [] instructions;

    public CodeBlock() {
        this.instructions = new Instruction[] {};
    }

    public CodeBlock(Instruction [] instructions) {
        this.instructions = instructions;
    }

    public Instruction[] getInstructions() {
        return instructions;
    }
}
