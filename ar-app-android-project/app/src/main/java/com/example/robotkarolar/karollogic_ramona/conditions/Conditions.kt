package com.example.robotkarolar.karollogic_ramona.conditions

import com.example.robotkarolar.karollogic_ramona.enums.ConditionTyp
import com.example.robotkarolar.karollogic_ramona.karolWorld.World

class Conditions: ConditionPart {

    var left: ConditionPart
    var right: ConditionPart

    var conditionTyp: ConditionTyp

    constructor(left: ConditionPart, right: ConditionPart, conditionTyp: ConditionTyp) {
        this.left = left
        this.right = right
        this.conditionTyp = conditionTyp
    }

    override fun isTrue(world: World): Boolean {
        if (conditionTyp == ConditionTyp.AND) {
            return left.isTrue(world) && right.isTrue(world)
        } else {
            return left.isTrue(world) || right.isTrue(world)
        }
    }

    override fun returnTextValue(): String {
        return (left.returnTextValue() + " " + conditionTyp.toString() + " " + right.returnTextValue())
    }
}