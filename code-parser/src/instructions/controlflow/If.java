package instructions.controlflow;

import instructions.Instruction;
import instructions.visitors.InstructionVisitor;
import instructions.expressions.Expression;

public class If extends Instruction {

    private final Expression condition;
    private final Instruction[] instructions;

    public If(Expression condition, Instruction[] instructions) {
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

    public static String getKeyword() {
        return "if";
    }
}
