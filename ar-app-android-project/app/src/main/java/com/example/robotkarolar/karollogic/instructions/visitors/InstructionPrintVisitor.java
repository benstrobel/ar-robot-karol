package com.example.robotkarolar.karollogic.instructions.visitors;

import com.example.robotkarolar.karollogic.instructions.Instruction;
import com.example.robotkarolar.karollogic.instructions.controlflow.CodeBlock;
import com.example.robotkarolar.karollogic.instructions.controlflow.If;
import com.example.robotkarolar.karollogic.instructions.controlflow.While;
import com.example.robotkarolar.karollogic.instructions.expressions.*;
import com.example.robotkarolar.karollogic.instructions.statements.*;

public class InstructionPrintVisitor implements InstructionVisitor{
    @Override
    public void accept(End end) {
        System.out.println("End");
    }

    @Override
    public void accept(LeftTurn leftTurn) {
        System.out.println("LeftTurn");
    }

    @Override
    public void accept(Lift lift) {
        System.out.println("Lift");
    }

    @Override
    public void accept(Place place) {
        System.out.println("PlaceWater");
    }

    @Override
    public void accept(PlaceStone placeStone) {
        System.out.println("PlaceStone");
    }

    @Override
    public void accept(RightTurn rightTurn) {
        System.out.println("RightTurn");
    }

    @Override
    public void accept(Step step) {
        System.out.println("Step");
    }

    @Override
    public void accept(Noop noop) {
        System.out.println("Noop");
    }

    @Override
    public void accept(And and) {
        and.getLeft().accept(this);
        System.out.println("And");
        and.getRight().accept(this);
    }

    @Override
    public void accept(IsBlock isBlock) {
        System.out.println("IsBlock");
    }

    @Override
    public void accept(IsEast isEast) {
        System.out.println("IsEast");
    }

    @Override
    public void accept(IsNorth isNorth) {
        System.out.println("IsNorth");
    }

    @Override
    public void accept(IsSouth isSouth) {
        System.out.println("IsSouth");
    }

    @Override
    public void accept(IsBorder isBorder) {
        System.out.println("IsBorder");
    }

    @Override
    public void accept(IsWest isWest) {
        System.out.println("IsWest");
    }

    @Override
    public void accept(Not not) {
        System.out.println("Not");
        not.getChild().accept(this);
    }

    @Override
    public void accept(Or or) {
        or.getLeft().accept(this);
        System.out.println("Or");
        or.getRight().accept(this);
    }

    @Override
    public void accept(True expressionTrue) {
        System.out.println("True");
    }

    @Override
    public void accept(False expressionFalse) {
        System.out.println("False");
    }

    @Override
    public void accept(EmptyExpression emptyExpression) {
        System.out.println("EmptyExpression");
    }

    @Override
    public void accept(If controlFlowIf) {
        System.out.println("If");
        controlFlowIf.getCondition().accept(this);
        System.out.println("{");
        for (Instruction instruction : controlFlowIf.getCodeBlock().getInstructions()) {
            instruction.accept(this);
        }
        System.out.println("}");
    }

    @Override
    public void accept(While controlFlowWhile) {
        System.out.println("While");
        controlFlowWhile.getCondition().accept(this);
        System.out.println("{");
        for (Instruction instruction : controlFlowWhile.getCodeBlock().getInstructions()) {
            instruction.accept(this);
        }
        System.out.println("}");
    }

    @Override
    public void accept(CodeBlock codeBlock) {
        for(Instruction instruction: codeBlock.getInstructions()) {
            instruction.accept(this);
        }
    }
}
