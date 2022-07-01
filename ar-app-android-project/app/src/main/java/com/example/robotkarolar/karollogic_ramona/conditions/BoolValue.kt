package com.example.robotkarolar.karollogic_ramona.conditions

import com.example.robotkarolar.karollogic_ramona.enums.DirectionTyp
import com.example.robotkarolar.karollogic_ramona.enums.ExpressionTyp
import com.example.robotkarolar.karollogic_ramona.karolWorld.World

class BoolValue: ConditionPart {

    var value: ExpressionTyp
    var not: Boolean = false

    constructor(value: ExpressionTyp) {
        when(value) {
            ExpressionTyp.NOTISBLOCK -> {
                this.value = ExpressionTyp.ISBLOCK
                not = true
            }
            ExpressionTyp.NOTISBOARDER -> {
                this.value = ExpressionTyp.ISBOARDER
                not = true
            }
            ExpressionTyp.NOTISEAST -> {
                this.value = ExpressionTyp.ISEAST
                not = true
            }
            ExpressionTyp.NOTISNORTH -> {
                this.value = ExpressionTyp.ISNORTH
                not = true
            }
            ExpressionTyp.NOTISSOUTH -> {
                this.value = ExpressionTyp.ISSOUTH
                not = true
            }
            ExpressionTyp.NOTISWEST -> {
                this.value = ExpressionTyp.ISWEST
                not = true
            }
            else -> this.value = value
        }
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

    override fun returnTextValue(): String {
        if (not) {
            return ("! " + value.toString())
        } else {
            return (value.toString())
        }
    }
}