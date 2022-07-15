package com.example.robotkarolar.karollogic.instructions.visitors;

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
import com.example.robotkarolar.karollogic.instructions.statements.PlaceGrass;
import com.example.robotkarolar.karollogic.instructions.statements.PlaceStone;
import com.example.robotkarolar.karollogic.instructions.statements.RightTurn;
import com.example.robotkarolar.karollogic.instructions.statements.Step;

public class ExpressionSanityVisitor implements InstructionVisitor{
    private boolean isSane = true;

    public boolean isSane() {
        return isSane;
    }

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
    public void accept(PlaceGrass place) {

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
        and.getLeft().accept(this);
        and.getRight().accept(this);
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
        not.getChild().accept(this);
    }

    @Override
    public void accept(Or or) {
        or.getLeft().accept(this);
        or.getRight().accept(this);
    }

    @Override
    public void accept(True expressionTrue) {

    }

    @Override
    public void accept(False expressionFalse) {

    }

    @Override
    public void accept(EmptyExpression emptyExpression) {
        isSane = false;
    }

    @Override
    public void accept(If controlFlowIf) {
        controlFlowIf.getCondition().accept(this);
        controlFlowIf.getCodeBlock().getInstructions().forEach(x -> x.accept(this));
    }

    @Override
    public void accept(While controlFlowWhile) {
        controlFlowWhile.getCondition().accept(this);
        controlFlowWhile.getCodeBlock().getInstructions().forEach(x -> x.accept(this));
    }

    @Override
    public void accept(CodeBlock codeBlock) {
        codeBlock.getInstructions().forEach(x -> x.accept(this));
    }
}
