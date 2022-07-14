package com.example.robotkarolar.karollogic.instructions.visitors

import com.example.robotkarolar.ar.*
import com.example.robotkarolar.karollogic.instructions.controlflow.CodeBlock
import com.example.robotkarolar.karollogic.instructions.controlflow.If
import com.example.robotkarolar.karollogic.instructions.controlflow.While
import com.example.robotkarolar.karollogic.instructions.expressions.*
import com.example.robotkarolar.karollogic.instructions.statements.*
import com.example.robotkarolar.karollogic.world.World

class InstructionStepperVisitor constructor(val world: World): InstructionVisitor {
    class StepperResult(val command: ArCommand?, val nextCodeBlock: CodeBlock?)

    var stepperResult: StepperResult? = null

    override fun accept(end: End) {
        stepperResult = StepperResult(end(), null)
    }
    override fun accept(leftTurn: LeftTurn) {
        world.leftTurn()
        stepperResult = StepperResult(rotateLeft(), null)
    }
    override fun accept(lift: Lift) {
        val lifted: Triple<Int, Int, Int>? = world.lift()
        if(lifted != null) {
            stepperResult = StepperResult(removeBlock(lifted.first,lifted.second,lifted.third), null)
        }
    }
    override fun accept(place: Place) {
        val placed: Triple<Int, Int, Int>? = world.place()
        if(placed != null) {
            stepperResult = StepperResult(placeBlock(placed.first,placed.second,placed.third, BlockType.WATER), null)
        }
    }

    override fun accept(placeStone: PlaceStone?) {
        val placedStone: Triple<Int, Int, Int>? = world.placeStone()
        if(placedStone != null) {
            stepperResult = StepperResult(placeBlock(placedStone.first,placedStone.second,placedStone.third, BlockType.STONE), null)
        }
    }

    override fun accept(placeGrass: PlaceGrass) {
        val placed: Triple<Int, Int, Int>? = world.placeGrass()
        if(placed != null) {
            stepperResult = StepperResult(placeBlock(placed.first,placed.second,placed.third, BlockType.GRASS), null)
        }
    }

    override fun accept(rightTurn: RightTurn) {
        world.rightTurn()
        stepperResult = StepperResult(rotateRight(), null)
    }
    override fun accept(step: Step) {
        val stepped: Triple<Int, Int, Int>? = world.step()
        if(stepped != null) {
            stepperResult = StepperResult(moveTo(stepped.first,stepped.second,stepped.third), null)
        }
    }
    override fun accept(noop: Noop) {}
    override fun accept(and: And) {}
    override fun accept(isBlock: IsBlock) {}
    override fun accept(isEast: IsEast) {}
    override fun accept(isNorth: IsNorth) {}
    override fun accept(isSouth: IsSouth) {}
    override fun accept(isBorder: IsBorder) {}
    override fun accept(isWest: IsWest) {}
    override fun accept(not: Not) {}
    override fun accept(or: Or) {}
    override fun accept(expressionTrue: True) {}
    override fun accept(expressionFalse: False) {}
    override fun accept(emptyExpression: EmptyExpression) {}
    override fun accept(controlFlowIf: If) {
        val expressionVisitor = ExpressionEvaulationVisitor(world)
        if(controlFlowIf.condition.accept(expressionVisitor)) {
            stepperResult = StepperResult(null, controlFlowIf.codeBlock)
        } else {
            stepperResult = StepperResult(null, null)
        }
    }
    override fun accept(controlFlowWhile: While) {
        val expressionVisitor = ExpressionEvaulationVisitor(world)
        if(controlFlowWhile.condition.accept(expressionVisitor)) {
            stepperResult = StepperResult(null, controlFlowWhile.codeBlock)
        } else {
            stepperResult = StepperResult(null, null)
        }
    }
    override fun accept(codeBlock: CodeBlock) {
        stepperResult = StepperResult(null, codeBlock)
    }
}