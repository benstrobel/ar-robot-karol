package com.example.robotkarolar.karollogic.instructions.visitors;

import com.example.robotkarolar.karollogic.instructions.Instruction;
import com.example.robotkarolar.karollogic.instructions.controlflow.CodeBlock;
import com.example.robotkarolar.karollogic.instructions.controlflow.If;
import com.example.robotkarolar.karollogic.instructions.controlflow.While;
import com.example.robotkarolar.karollogic.instructions.expressions.And;
import com.example.robotkarolar.karollogic.instructions.expressions.EmptyExpression;
import com.example.robotkarolar.karollogic.instructions.expressions.False;
import com.example.robotkarolar.karollogic.instructions.expressions.IsBlock;
import com.example.robotkarolar.karollogic.instructions.expressions.IsBorder;
import com.example.robotkarolar.karollogic.instructions.expressions.IsEast;
import com.example.robotkarolar.karollogic.instructions.expressions.IsNorth;
import com.example.robotkarolar.karollogic.instructions.expressions.IsSouth;
import com.example.robotkarolar.karollogic.instructions.expressions.IsWest;
import com.example.robotkarolar.karollogic.instructions.expressions.Not;
import com.example.robotkarolar.karollogic.instructions.expressions.Or;
import com.example.robotkarolar.karollogic.instructions.expressions.True;
import com.example.robotkarolar.karollogic.instructions.statements.End;
import com.example.robotkarolar.karollogic.instructions.statements.LeftTurn;
import com.example.robotkarolar.karollogic.instructions.statements.Lift;
import com.example.robotkarolar.karollogic.instructions.statements.Noop;
import com.example.robotkarolar.karollogic.instructions.statements.Place;
import com.example.robotkarolar.karollogic.instructions.statements.PlaceStone;
import com.example.robotkarolar.karollogic.instructions.statements.RightTurn;
import com.example.robotkarolar.karollogic.instructions.statements.Step;

public class InstructionStepperResetVisitor implements InstructionVisitor{
    @Override
    public void accept(End end) {

    }

    @Override
    public void accept(LeftTurn leftTurn) {

    }

    @Override
    public void accept(Lift lift) {

    }

    @Override
    public void accept(Place place) {

    }

    @Override
    public void accept(PlaceStone placeStone) {

    }

    @Override
    public void accept(RightTurn rightTurn) {

    }

    @Override
    public void accept(Step step) {

    }

    @Override
    public void accept(Noop noop) {

    }

    @Override
    public void accept(And and) {

    }

    @Override
    public void accept(IsBlock isBlock) {

    }

    @Override
    public void accept(IsEast isEast) {

    }

    @Override
    public void accept(IsNorth isNorth) {

    }

    @Override
    public void accept(IsSouth isSouth) {

    }

    @Override
    public void accept(IsBorder isBorder) {

    }

    @Override
    public void accept(IsWest isWest) {

    }

    @Override
    public void accept(Not not) {

    }

    @Override
    public void accept(Or or) {

    }

    @Override
    public void accept(True expressionTrue) {

    }

    @Override
    public void accept(False expressionFalse) {

    }

    @Override
    public void accept(EmptyExpression emptyExpression) {

    }

    @Override
    public void accept(If controlFlowIf) {
        controlFlowIf.getCodeBlock().resetPointer();
        for(Instruction i : controlFlowIf.getCodeBlock().getInstructions()) {
            i.accept(this);
        }
    }

    @Override
    public void accept(While controlFlowWhile) {
        controlFlowWhile.getCodeBlock().resetPointer();
        for(Instruction i : controlFlowWhile.getCodeBlock().getInstructions()) {
            i.accept(this);
        }
    }

    @Override
    public void accept(CodeBlock codeBlock) {
        codeBlock.resetPointer();
        for(Instruction i : codeBlock.getInstructions()) {
            i.accept(this);
        }
    }
}
