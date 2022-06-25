package com.example.robotkarolar.karollogic.conditions

import com.example.robotkarolar.karollogic.enums.DirectionTyp
import com.example.robotkarolar.karollogic.enums.ExpressionTyp
import com.example.robotkarolar.karollogic.karolWorld.World

class BoolValue: ConditionPart {

    var value: ExpressionTyp
    var not: Boolean

    constructor(value: ExpressionTyp, not: Boolean) {
        this.value = value
        this.not = not
    }

    override fun isTrue(world: World): Boolean {
        var returnTyp = false
        when (value) {
            ExpressionTyp.ISBLOCK -> {returnTyp = world.isBlock()}
            ExpressionTyp.ISBOARDER -> {returnTyp = world.isBorder()}
            ExpressionTyp.ISEAST -> {returnTyp = world.isDirection(DirectionTyp.EAST)}
            ExpressionTyp.ISNORTH -> {returnTyp = world.isDirection(DirectionTyp.NORTH)}
            ExpressionTyp.ISSOUTH -> {returnTyp = world.isDirection(DirectionTyp.SOUTH)}
            ExpressionTyp.ISWEST -> {returnTyp = world.isDirection(DirectionTyp.WEST)}
            else -> print("Condition not possible")
        }

        if (not) {returnTyp = !returnTyp}

        return returnTyp
    }
}