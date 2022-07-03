package com.example.robotkarolar.karollogic_ben;

import com.example.robotkarolar.karollogic_ben.instructions.Instruction;
import com.example.robotkarolar.karollogic_ben.instructions.controlflow.CodeBlock;
import com.example.robotkarolar.karollogic_ben.instructions.visitors.InstructionExecuteVisitor;
import com.example.robotkarolar.karollogic_ben.world.World;

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
