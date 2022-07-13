package com.example.robotkarolar.karollogic;

import static com.example.robotkarolar.ar.ArCommandKt.end;
import static com.example.robotkarolar.ar.ArCommandKt.rotateLeft;

import com.example.robotkarolar.ar.ArCommand;
import com.example.robotkarolar.karollogic.instructions.Instruction;
import com.example.robotkarolar.karollogic.instructions.controlflow.CodeBlock;
import com.example.robotkarolar.karollogic.instructions.controlflow.ControlFlow;
import com.example.robotkarolar.karollogic.instructions.controlflow.If;
import com.example.robotkarolar.karollogic.instructions.controlflow.While;
import com.example.robotkarolar.karollogic.instructions.visitors.ExpressionEvaulationVisitor;
import com.example.robotkarolar.karollogic.instructions.visitors.InstructionExecuteVisitor;
import com.example.robotkarolar.karollogic.instructions.visitors.InstructionStepperResetVisitor;
import com.example.robotkarolar.karollogic.instructions.visitors.InstructionStepperVisitor;
import com.example.robotkarolar.karollogic.instructions.visitors.InstructionVisitor;
import com.example.robotkarolar.karollogic.world.World;

public class Interpreter {

    private World world;

    private CodeBlock currentCodeBlock;

    public Interpreter(CodeBlock codeBlock, World world) {
        this.currentCodeBlock = codeBlock;
        this.world = world;
    }

    public void reset() {
        InstructionStepperResetVisitor visitor = new InstructionStepperResetVisitor();
        currentCodeBlock.accept(visitor);
    }

    public ArCommand nextStep() {
        if(currentCodeBlock.getInstructions().size() <= currentCodeBlock.getPointer()) {

            if(currentCodeBlock.getParent() == null) {
                return end();
            } else {
                if(currentCodeBlock.getParent() instanceof If) {
                    currentCodeBlock = ((ControlFlow)((If)currentCodeBlock.getParent()).getParent()).getCodeBlock();
                } else if (currentCodeBlock.getParent() instanceof While) {
                    While controlFlowWhile = ((While)currentCodeBlock.getParent());
                    ExpressionEvaulationVisitor expressionVisitor = new ExpressionEvaulationVisitor(world);
                    if(controlFlowWhile.getCondition().accept(expressionVisitor)) {
                        currentCodeBlock.resetPointer();
                    } else {
                        currentCodeBlock = ((ControlFlow)controlFlowWhile.getParent()).getCodeBlock();
                    }
                } else if (currentCodeBlock.getParent() instanceof CodeBlock) {
                    currentCodeBlock = ((ControlFlow)((If)currentCodeBlock.getParent()).getParent()).getCodeBlock();
                }
            }
            return nextStep();
        } else {
            Instruction instruction = currentCodeBlock.getInstructions().get(currentCodeBlock.getPointer());
            InstructionStepperVisitor stepper = new InstructionStepperVisitor(world);
            instruction.accept(stepper);
            InstructionStepperVisitor.StepperResult result = stepper.getStepperResult();
            currentCodeBlock.incrementPointer();
            if(result != null && result.getCommand() != null) {
                return result.getCommand();
            } else if(result != null && result.getNextCodeBlock() != null) {
                currentCodeBlock = result.getNextCodeBlock();
                return nextStep();
            } else {
                // Only happens when skipping over if / while blocks because condition isn't met
                return nextStep();
            }
        }
    }
}
