package instructions.expressions;

import instructions.visitors.InstructionVisitor;

public class False extends Expression{
    @Override
    public void accept(InstructionVisitor visitor) {
        visitor.accept(this);
    }

    @Override
    public boolean accept(ExpressionVisitor visitor) {
        return visitor.accept(this);
    }
}
