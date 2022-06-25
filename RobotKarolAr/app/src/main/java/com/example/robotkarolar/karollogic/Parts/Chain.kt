package com.example.robotkarolar.karollogic.Parts

import com.example.robotkarolar.karollogic.enums.CommandType
import com.example.robotkarolar.karollogic.karolWorld.World

class Chain: CodeParts {
    var code: MutableList<CodeParts>

    constructor(code: MutableList<CodeParts>) {
        this.code = code
    }

    //run Code and return list of commands executed
    override fun returnCommands(world: World): MutableList<CommandType> {
        var returnList: MutableList<CommandType> = mutableListOf()
        code.forEach{returnList += it.returnCommands(world)}

        return  returnList
    }
}