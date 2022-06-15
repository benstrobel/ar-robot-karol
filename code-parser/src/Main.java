import instructions.Instruction;
import instructions.visitors.InstructionPrintVisitor;
import interpreter.Interpreter;
import interpreter.Parser;
import world.Entity;
import world.World;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {

        String[] lines = Files.lines(Paths.get(System.getProperty("user.dir") + "\\testinput.txt")).collect(Collectors.toList()).toArray(String[]::new);

        Instruction[] instructions = Parser.parse(lines);

        InstructionPrintVisitor printer = new InstructionPrintVisitor();
        for(Instruction instruction: instructions) {
            instruction.accept(printer);
        }

        World world = new World();
        Entity robot = world.addEntity(0,0);
        world.setSelectedEntity(robot);

        Interpreter.interpret(instructions, world);

    }
}
