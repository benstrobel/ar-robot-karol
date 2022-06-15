package instructions.visitors;

import instructions.expressions.*;

public class ExpressionEvaulationVisitor implements ExpressionVisitor {
    @Override
    public boolean accept(And and) {
        return and.getLeft().accept(this) && and.getRight().accept(this);
    }

    @Override
    public boolean accept(IsBlock isBlock) {
        // TODO
        return false;
    }

    @Override
    public boolean accept(IsEast isEast) {
        // TODO
        return false;
    }

    @Override
    public boolean accept(IsNorth isNorth) {
        // TODO
        return false;
    }

    @Override
    public boolean accept(IsSouth isSouth) {
        // TODO
        return false;
    }

    @Override
    public boolean accept(IsBorder isBorder) {
        // TODO
        return false;
    }

    @Override
    public boolean accept(IsWest isWest) {
        // TODO
        return false;
    }

    @Override
    public boolean accept(Not not) {
        return !not.getChild().accept(this);
    }

    @Override
    public boolean accept(Or or) {
        return or.getLeft().accept(this) && or.getRight().accept(this);
    }

    @Override
    public boolean accept(True expressionTrue) {
        return true;
    }

    @Override
    public boolean accept(False expressionFalse) {
        return false;
    }
}
