package com.example.robotkarolar.karollogic.instructions.visitors;

import com.example.robotkarolar.karollogic.instructions.Instruction;
import com.example.robotkarolar.karollogic.instructions.controlflow.CodeBlock;
import com.example.robotkarolar.karollogic.instructions.controlflow.If;
import com.example.robotkarolar.karollogic.instructions.controlflow.While;
import com.example.robotkarolar.karollogic.instructions.expressions.And;
import com.example.robotkarolar.karollogic.instructions.expressions.EmptyExpression;
import com.example.robotkarolar.karollogic.instructions.expressions.Expression;
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
import com.example.robotkarolar.karollogic.instructions.statements.RightTurn;
import com.example.robotkarolar.karollogic.instructions.statements.Step;

public class ExpressionNameRenderVisitor implements InstructionVisitor {

    String renderedName;

    public ExpressionNameRenderVisitor(Instruction expression) {
        expression.accept(this);
    }

    public String get() {
        return renderedName;
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
        renderedName = "AND";
    }

    @Override
    public void accept(IsBlock isBlock) {
        renderedName = "ISBLOCK";
    }

    @Override
    public void accept(IsEast isEast) {
        renderedName = "ISEAST";
    }

    @Override
    public void accept(IsNorth isNorth) {
        renderedName = "ISNORTH";
    }

    @Override
    public void accept(IsSouth isSouth) {
        renderedName = "ISSOUTH";
    }

    @Override
    public void accept(IsBorder isBorder) {
        renderedName = "ISBORDER";
    }

    @Override
    public void accept(IsWest isWest) {
        renderedName = "ISWEST";
    }

    @Override
    public void accept(Not not) {
        renderedName = "NOT";
    }

    @Override
    public void accept(Or or) {
        renderedName = "OR";
    }

    @Override
    public void accept(True expressionTrue) {
        renderedName = "TRUE";
    }

    @Override
    public void accept(False expressionFalse) {
        renderedName = "FALSE";
    }

    @Override
    public void accept(EmptyExpression emptyExpression) {
        renderedName = "___";
    }

    @Override
    public void accept(If controlFlowIf) {
        renderedName = "IF";
    }

    @Override
    public void accept(While controlFlowWhile) {
        renderedName = "WHILE";
    }

    @Override
    public void accept(CodeBlock codeBlock) {

    }
}
