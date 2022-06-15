package interpreter;

import instructions.Instruction;
import instructions.visitors.InstructionExecuteVisitor;
import world.World;

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
