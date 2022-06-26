package com.example.robotkarolar.karollogic_ben.interpreter;

import com.example.robotkarolar.karollogic_ben.instructions.Instruction;
import com.example.robotkarolar.karollogic_ben.instructions.visitors.InstructionExecuteVisitor;
import com.example.robotkarolar.karollogic_ben.world.World;

public class Interpreter {

    public static void interpret(Instruction[] instructions, World world) {
        InstructionExecuteVisitor executeVisitor = new InstructionExecuteVisitor(world);
        for(Instruction instruction: instructions) {
            System.out.println("Exeuting statement: " + instruction.toString());
            world.printWorld();
            instruction.accept(executeVisitor);
            if(executeVisitor.isEnd()) break;
        }
    }
}
