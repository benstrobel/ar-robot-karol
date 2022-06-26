package com.example.robotkarolar.karollogic_ben.instructions.expressions;

public interface ExpressionVisitor {

    boolean accept(And and);
    boolean accept(IsBlock isBlock);
    boolean accept(IsEast isEast);
    boolean accept(IsNorth isNorth);
    boolean accept(IsSouth isSouth);
    boolean accept(IsBorder isBorder);
    boolean accept(IsWest isWest);
    boolean accept(Not not);
    boolean accept(Or or);
    boolean accept(True expressionTrue);
    boolean accept(False expressionFalse);

}
