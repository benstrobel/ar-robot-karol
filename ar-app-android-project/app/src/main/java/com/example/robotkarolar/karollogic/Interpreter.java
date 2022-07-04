package com.example.robotkarolar.karollogic;

import com.example.robotkarolar.karollogic.instructions.Instruction;
import com.example.robotkarolar.karollogic.instructions.controlflow.CodeBlock;
import com.example.robotkarolar.karollogic.instructions.visitors.InstructionExecuteVisitor;
import com.example.robotkarolar.karollogic.world.World;

public class Interpreter {

    public static void interpret(CodeBlock codeBlock, World world) {
        InstructionExecuteVisitor executeVisitor = new InstructionExecuteVisitor(world);
        for(Instruction instruction: codeBlock.getInstructions()) {
            System.out.println("Exeuting statement: " + instruction.toString());
            world.printWorld();
            instruction.accept(executeVisitor);
            if(executeVisitor.isEnd()) break;
        }
    }
}
