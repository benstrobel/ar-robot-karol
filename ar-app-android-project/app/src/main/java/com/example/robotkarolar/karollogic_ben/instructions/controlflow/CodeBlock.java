package com.example.robotkarolar.karollogic_ben.instructions.controlflow;

import com.example.robotkarolar.karollogic_ben.instructions.Instruction;
import com.example.robotkarolar.karollogic_ben.instructions.visitors.InstructionVisitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CodeBlock extends Instruction {

    private List<Instruction> instructions;

    public CodeBlock() {
        this.instructions = new ArrayList<>();
    }

    public CodeBlock(Instruction [] instructions) {
        this.instructions = Arrays.asList(instructions);
    }

    public Instruction[] getInstructions() {
        return instructions.toArray(new Instruction[]{});
    }

    public void addInstruction(Instruction instruction) {
        instructions.add(instruction);
    }

    public void addInstruction(Instruction instruction, Instruction afterInstruction) {
        int foundInstructionIndex = instructions.indexOf(afterInstruction);
        instructions.add(foundInstructionIndex+1, instruction);
    }

    public void removeInstruction(Instruction instruction) {
        instructions.remove(instruction);
    }

    public Instruction getNextAfter(Instruction instruction) {
        int foundInstructionIndex = instructions.indexOf(instruction);
        if(foundInstructionIndex == -1 || instructions.size() -1 < foundInstructionIndex + 1) return null;
        return instructions.get(foundInstructionIndex + 1);
    }

    public Instruction getPreviousBefore(Instruction instruction) {
        int foundInstructionIndex = instructions.indexOf(instruction);
        if(foundInstructionIndex == -1 || 0 > foundInstructionIndex - 1) return null;
        return instructions.get(foundInstructionIndex - 1);
    }

    public int size() {
        return instructions.size();
    }

    @Override
    public void accept(InstructionVisitor visitor) {
        visitor.accept(this);
    }
}
