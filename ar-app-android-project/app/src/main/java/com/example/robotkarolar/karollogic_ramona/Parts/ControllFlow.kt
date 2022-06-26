package com.example.robotkarolar.karollogic_ramona.Parts

import com.example.robotkarolar.karollogic_ramona.conditions.ConditionPart
import com.example.robotkarolar.karollogic_ramona.enums.CommandType
import com.example.robotkarolar.karollogic_ramona.enums.ControllFlowType
import com.example.robotkarolar.karollogic_ramona.karolWorld.World

class ControllFlow: CodeParts {
    var controllFlowType: ControllFlowType

    var condition: ConditionPart
    var codeParts: CodeParts

    constructor(controllFlowType: ControllFlowType, condition: ConditionPart, codeParts: CodeParts) {
        this.controllFlowType = controllFlowType
        this.condition = condition
        this.codeParts = codeParts
    }

    override fun returnCommands(world: World): MutableList<CommandType> {
        var returnList: MutableList<CommandType> = mutableListOf()

        if (condition.isTrue(world)) { //check if codition is true
            returnList += codeParts.returnCommands(world) //run once throu the Coming parts

            if (controllFlowType == ControllFlowType.WHILE) { //if while repeat
                returnList += this.returnCommands(world)
            }
        }

        return returnList
    }
}