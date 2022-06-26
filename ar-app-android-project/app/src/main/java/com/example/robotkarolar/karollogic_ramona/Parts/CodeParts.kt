package com.example.robotkarolar.karollogic_ramona.Parts

import com.example.robotkarolar.karollogic_ramona.enums.CommandType
import com.example.robotkarolar.karollogic_ramona.karolWorld.World

interface CodeParts {
    fun returnCommands(world: World): MutableList<CommandType>
}