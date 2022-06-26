package com.example.robotkarolar.karollogic_ben.instructions.visitors;

import com.example.robotkarolar.karollogic_ben.instructions.expressions.*;
import com.example.robotkarolar.karollogic_ben.world.World;

public class ExpressionEvaulationVisitor implements ExpressionVisitor {

    private World world;

    public ExpressionEvaulationVisitor(World world) {
        this.world = world;
    }

    @Override
    public boolean accept(And and) {
        return and.getLeft().accept(this) && and.getRight().accept(this);
    }

    @Override
    public boolean accept(IsBlock isBlock) {
        return world.isBlock();
    }

    @Override
    public boolean accept(IsEast isEast) {
        return world.isEast();
    }

    @Override
    public boolean accept(IsNorth isNorth) {
        return world.isNorth();
    }

    @Override
    public boolean accept(IsSouth isSouth) {
        return world.isSouth();
    }

    @Override
    public boolean accept(IsBorder isBorder) {
        return world.isBorder();
    }

    @Override
    public boolean accept(IsWest isWest) {
        return world.isWest();
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
