package com.example.robotkarolar.karollogic.instructions.visitors;

import com.example.robotkarolar.karollogic.instructions.controlflow.CodeBlock;
import com.example.robotkarolar.karollogic.instructions.controlflow.If;
import com.example.robotkarolar.karollogic.instructions.controlflow.While;
import com.example.robotkarolar.karollogic.instructions.expressions.*;
import com.example.robotkarolar.karollogic.instructions.statements.*;

public interface InstructionVisitor {

    // Statements
    void accept(End end);
    void accept(LeftTurn leftTurn);
    void accept(Lift lift);
    void accept(Place place);
    void accept(RightTurn rightTurn);
    void accept(Step step);
    void accept(Noop noop);

    // Expressions
    void accept(And and);
    void accept(IsBlock isBlock);
    void accept(IsEast isEast);
    void accept(IsNorth isNorth);
    void accept(IsSouth isSouth);
    void accept(IsBorder isBorder);
    void accept(IsWest isWest);
    void accept(Not not);
    void accept(Or or);
    void accept(True expressionTrue);
    void accept(False expressionFalse);

    // Control Flow
    void accept(If controlFlowIf);
    void accept(While controlFlowWhile);
    void accept(CodeBlock codeBlock);
}
