package com.example.robotkarolar.karollogic_ramona.conditions

import com.example.robotkarolar.karollogic_ramona.enums.ConditionType
import com.example.robotkarolar.karollogic_ramona.karolWorld.World

class Conditions: ConditionPart {

    var left: ConditionPart
    var right: ConditionPart

    var conditionType: ConditionType

    constructor(left: ConditionPart, right: ConditionPart, conditionType: ConditionType) {
        this.left = left
        this.right = right
        this.conditionType = conditionType
    }

    override fun isTrue(world: World): Boolean {
        if (conditionType == ConditionType.AND) {
            return left.isTrue(world) && right.isTrue(world)
        } else {
            return left.isTrue(world) || right.isTrue(world)
        }
    }

    override fun returnTextValue(): String {
        return (left.returnTextValue() + " " + conditionType.toString() + " " + right.returnTextValue())
    }
}