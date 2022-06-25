package instructions.visitors;

import instructions.Instruction;
import instructions.controlflow.If;
import instructions.controlflow.While;
import instructions.expressions.*;
import instructions.statements.*;

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
        System.out.println("Place");
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
    public void accept(If controlFlowIf) {
        System.out.println("If");
        controlFlowIf.getCondition().accept(this);
        System.out.println("{");
        for (Instruction instruction : controlFlowIf.getInstructions()) {
            instruction.accept(this);
        }
        System.out.println("}");
    }

    @Override
    public void accept(While controlFlowWhile) {
        System.out.println("While");
        controlFlowWhile.getCondition().accept(this);
        System.out.println("{");
        for (Instruction instruction : controlFlowWhile.getInstructions()) {
            instruction.accept(this);
        }
        System.out.println("}");
    }
}
