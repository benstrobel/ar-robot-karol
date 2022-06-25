package instructions.controlflow;

import instructions.Instruction;
import instructions.visitors.InstructionVisitor;
import instructions.expressions.Expression;

public class While extends Instruction {

    private final Expression condition;
    private final Instruction[] instructions;

    public While(Expression condition, Instruction[] instructions) {
        this.condition = condition;
        this.instructions = instructions;
    }

    public Expression getCondition() {
        return condition;
    }

    public Instruction[] getInstructions() {
        return instructions;
    }

    @Override
    public void accept(InstructionVisitor visitor) {
        visitor.accept(this);
    }
}
