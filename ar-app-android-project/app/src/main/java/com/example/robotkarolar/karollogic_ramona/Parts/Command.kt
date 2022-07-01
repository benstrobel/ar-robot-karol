package com.example.robotkarolar.karollogic_ramona.Parts

import androidx.compose.ui.graphics.Color
import com.example.robotkarolar.karollogic_ramona.enums.CommandType
import com.example.robotkarolar.karollogic_ramona.karolWorld.World

class Command: CodeParts {
    var command: CommandType

    override var index = 0

    constructor(commandType: CommandType) {
        this.command = commandType
    }

    override fun returnCommands(world: World): MutableList<CommandType> {
        world.command(command)
        return mutableListOf(command)
    }

    override fun returnColor(): Color {
        when (command) {
            CommandType.STEP -> return Color(0xFF3C7AF5)
            CommandType.PLACEGRAS -> return Color(0xFF6092F5)
            CommandType.PLACESTONE -> return Color(0xFF7AA4F7)
            CommandType.PLACEWATER -> return Color(0xFF8BACEE)
            CommandType.REMOVE -> return Color(0xFFA4BFF7)
            CommandType.TURN -> return Color(0xFFA3BEF3)
            CommandType.TURNLEFT -> return Color(0xFF395388)
            CommandType.TURNRIGHT -> return Color(0xFF839CCC)
            else -> return Color(0xFFFFFFFF) //"No CommandTyp"
        }
    }

    override fun returnTextValue(): String {
        return command.toString()
    }

    override fun size(): Int {
        return 1
    }

    override fun printAll() {
        println(returnTextValue() + " : " + index)
    }

    override fun insertAt(goalIndex: Int, codeParts: CodeParts) {
        println("Error: Shouldnt be called")
    }

    override fun updateIndex(lastIndex: Int) {
        index = lastIndex + 1
    }
}