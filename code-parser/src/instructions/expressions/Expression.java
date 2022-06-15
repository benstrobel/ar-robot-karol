package instructions.expressions;

import instructions.Instruction;

public abstract class Expression extends Instruction {
    public abstract boolean accept(ExpressionVisitor visitor);
}
