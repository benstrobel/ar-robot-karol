package instructions.statements;

import instructions.Instruction;
import instructions.visitors.InstructionVisitor;

public class LeftTurn extends Instruction {
    @Override
    public void accept(InstructionVisitor visitor) {
        visitor.accept(this);
    }
}
