package com.example.robotkarolar.karollogic.instructions.controlflow;

import com.example.robotkarolar.karollogic.instructions.Instruction;
import com.example.robotkarolar.karollogic.instructions.visitors.InstructionVisitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CodeBlock extends ControlFlow {

    private List<Instruction> instructions;

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

    public Instruction getNextAfter(Instruction instruction) {
        int foundInstructionIndex = instructions.indexOf(instruction);
        if(foundInstructionIndex == -1 || instructions.size() -1 < foundInstructionIndex + 1) {
         if(getParent() == null) {
             return null;
         } else {
             return getParent();
         }
        }

        Instruction next = instructions.get(foundInstructionIndex + 1);
        if(next instanceof ControlFlow) {
            CodeBlock cb = ((ControlFlow)next).getCodeBlock();
            if(cb.size() > 0) {
              return cb.getInstructions()[0];
            } else {
                return next;
            }
        } else {
            return next;
        }
    }

    public Instruction getPreviousBefore(Instruction instruction) {
        return getPreviousBefore(instruction, false);
    }

    public Instruction getPreviousBefore(Instruction instruction, boolean hasSteppedOut) {

        if(instruction instanceof ControlFlow && !hasSteppedOut) {
            CodeBlock cb = ((ControlFlow)instruction).getCodeBlock();
            if(cb.size() <= 1) { // Noop is always in codeblock
                return instruction;
            } else {
                return cb.instructions.get(cb.size()-1);
            }
        }

        int foundInstructionIndex = instructions.indexOf(instruction);
        if(foundInstructionIndex == -1 || 0 > foundInstructionIndex - 1){
            if(getParent() == null) {
                return null;
            } else {
                return getParent().getParent().getCodeBlock().getPreviousBefore(getParent(), true);
            }
        }
        return instructions.get(foundInstructionIndex - 1);
    }

    public int size() {
        return instructions.size();
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
