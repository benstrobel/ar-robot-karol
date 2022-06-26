package com.example.robotkarolar.karollogic.conditions

import com.example.robotkarolar.karollogic.karolWorld.World

interface ConditionPart {
    fun isTrue(world: World): Boolean
}