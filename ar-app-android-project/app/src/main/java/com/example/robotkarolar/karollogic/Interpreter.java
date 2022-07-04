package com.example.robotkarolar.karollogic;

import static com.example.robotkarolar.ar.ArCommandKt.rotateLeft;

import com.example.robotkarolar.ar.ArCommand;
import com.example.robotkarolar.karollogic.instructions.Instruction;
import com.example.robotkarolar.karollogic.instructions.controlflow.CodeBlock;
import com.example.robotkarolar.karollogic.instructions.visitors.InstructionExecuteVisitor;
import com.example.robotkarolar.karollogic.world.World;

public class Interpreter {

    public ArCommand nextStep() {
        // TODO
        return rotateLeft();
    }

    public static void interpret(CodeBlock codeBlock, World world) {
        InstructionExecuteVisitor executeVisitor = new InstructionExecuteVisitor(world);
        for(Instruction instruction: codeBlock.getInstructions()) {
            instruction.accept(executeVisitor);
            if(executeVisitor.isEnd()) break;
            // arstuff.render(world)
        }
    }
}
