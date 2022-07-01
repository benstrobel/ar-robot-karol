package com.example.robotkarolar.karollogic_ramona.conditions

import com.example.robotkarolar.karollogic_ramona.karolWorld.World

interface ConditionPart {
    fun isTrue(world: World): Boolean

    fun returnTextValue(): String
}