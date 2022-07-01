package com.example.robotkarolar.karollogic_ramona.Parts

import androidx.compose.ui.graphics.Color
import com.example.robotkarolar.karollogic_ramona.enums.CommandType
import com.example.robotkarolar.karollogic_ramona.karolWorld.World

interface CodeParts {
    var index: Int

    fun returnCommands(world: World): MutableList<CommandType>

    fun returnColor(): Color
    fun returnTextValue(): String

    fun insertAt(goalIndex: Int, codeParts: CodeParts)
    fun updateIndex(lastIndex: Int)
    fun size(): Int
    fun printAll()
}