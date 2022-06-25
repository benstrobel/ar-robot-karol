package instructions;

import instructions.visitors.InstructionVisitor;

public abstract class Instruction {
    public abstract void accept(InstructionVisitor visitor);
}
