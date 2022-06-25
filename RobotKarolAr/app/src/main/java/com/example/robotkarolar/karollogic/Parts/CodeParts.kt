package com.example.robotkarolar.karollogic.Parts

import com.example.robotkarolar.karollogic.enums.CommandType
import com.example.robotkarolar.karollogic.karolWorld.World

interface CodeParts {
    fun returnCommands(world: World): MutableList<CommandType>
}