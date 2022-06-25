package com.example.robotkarolar.karollogic.Parts

import com.example.robotkarolar.karollogic.enums.CommandType
import com.example.robotkarolar.karollogic.karolWorld.World

class Command: CodeParts {
    var command: CommandType

    constructor(commandType: CommandType) {
        this.command = commandType
    }

    override fun returnCommands(world: World): MutableList<CommandType> {
        world.command(command)
        return mutableListOf(command)
    }
}