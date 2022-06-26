package com.example.robotkarolar.karollogic_ramona.Parts

import com.example.robotkarolar.karollogic_ramona.enums.CommandType
import com.example.robotkarolar.karollogic_ramona.karolWorld.World

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