package com.example.robotkarolar.karollogic.instructions.visitors;

import com.example.robotkarolar.karollogic.instructions.Instruction;
import com.example.robotkarolar.karollogic.instructions.controlflow.CodeBlock;
import com.example.robotkarolar.karollogic.instructions.controlflow.If;
import com.example.robotkarolar.karollogic.instructions.controlflow.While;
import com.example.robotkarolar.karollogic.instructions.expressions.*;
import com.example.robotkarolar.karollogic.instructions.statements.*;
import com.example.robotkarolar.karollogic.Interpreter;
import com.example.robotkarolar.karollogic.world.World;

public class InstructionExecuteVisitor implements InstructionVisitor{

    private final World world;

    public InstructionExecuteVisitor(World world) {
        this.world = world;
    }

    private boolean end = false;

    public boolean isEnd() {
        return end;
    }

    @Override
    public void accept(End end) {
        this.end = true;
    }

    @Override
    public void accept(LeftTurn leftTurn) {
        world.leftTurn();
    }

    @Override
    public void accept(Lift lift) {
        world.lift();
    }

    @Override
    public void accept(Place place) {
        world.place();
    }

    @Override
    public void accept(PlaceStone placeStone) {
        world.placeStone();
    }

    @Override
    public void accept(RightTurn rightTurn) {
        world.rightTurn();
    }

    @Override
    public void accept(Step step) {
        world.step();
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
        ExpressionEvaulationVisitor evaulationVisitor = new ExpressionEvaulationVisitor(world);
        if(controlFlowIf.getCondition().accept(evaulationVisitor)){
            controlFlowIf.getCodeBlock().accept(this);
        }
    }

    @Override
    public void accept(While controlFlowWhile) {
        ExpressionEvaulationVisitor evaulationVisitor = new ExpressionEvaulationVisitor(world);
        while(!end && controlFlowWhile.getCondition().accept(evaulationVisitor)){
            controlFlowWhile.getCodeBlock().accept(this);
        }
    }

    @Override
    public void accept(CodeBlock codeBlock) {
        for(Instruction instruction: codeBlock.getInstructions()) {
            if(!end) {
                instruction.accept(this);
            }
        }
    }
}
