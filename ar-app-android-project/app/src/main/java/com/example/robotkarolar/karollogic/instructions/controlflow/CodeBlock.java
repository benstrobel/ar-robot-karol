package com.example.robotkarolar.karollogic.instructions.controlflow;

import com.example.robotkarolar.karollogic.instructions.Instruction;
import com.example.robotkarolar.karollogic.instructions.expressions.Expression;
import com.example.robotkarolar.karollogic.instructions.visitors.InstructionVisitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CodeBlock extends ControlFlow {

    private List<Instruction> instructions;
    private int pointer = 0;

    public CodeBlock() {
        this.instructions = new ArrayList<>();
    }

    public CodeBlock(Instruction [] instructions) {
        this.instructions = new ArrayList<>();
        Arrays.stream(instructions).forEach(this::addInstruction);
    }

    public Instruction[] getInstructions() {
        return instructions.toArray(new Instruction[]{});
    }

    public void addInstruction(Instruction instruction) {
        instructions.add(instruction);
        instruction.setParent(this);
    }

    public void addInstruction(Instruction instruction, Instruction afterInstruction) {
        int foundInstructionIndex = instructions.indexOf(afterInstruction);
        instructions.add(foundInstructionIndex+1, instruction);
        instruction.setParent(this);
    }

    public void removeInstruction(Instruction instruction) {
        instructions.remove(instruction);
    }

    public int size() {
        return instructions.size();
    }

    public int getPointer() {
        return pointer;
    }

    public void resetPointer() {
        this.pointer = 0;
    }

    public void incrementPointer() {
        this.pointer++;
    }

    @Override
    public void accept(InstructionVisitor visitor) {
        visitor.accept(this);
    }

    @Override
    public CodeBlock getCodeBlock() {
        return this;
    }
}
