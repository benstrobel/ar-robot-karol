package com.example.robotkarolar.karollogic_ramona.Parts

import androidx.compose.ui.graphics.Color
import com.example.robotkarolar.karollogic_ramona.conditions.ConditionPart
import com.example.robotkarolar.karollogic_ramona.enums.CommandType
import com.example.robotkarolar.karollogic_ramona.enums.ControllFlowType
import com.example.robotkarolar.karollogic_ramona.karolWorld.World

class ControllFlow: CodeParts {
    var controllFlowType: ControllFlowType

    var condition: ConditionPart
    var codeParts: Chain

    override var index = 0
    override var indexEnd = 0

    constructor(controllFlowType: ControllFlowType, condition: ConditionPart, codeParts: Chain) {
        this.controllFlowType = controllFlowType
        this.condition = condition
        this.codeParts = codeParts
    }

    override fun returnCommands(world: World): MutableList<CommandType> {
        var returnList: MutableList<CommandType> = mutableListOf()

        if (condition.isTrue(world)) { //check if codition is true
            returnList += codeParts.returnCommands(world) //run once throu the Coming parts

            if (controllFlowType == ControllFlowType.WHILE) { //if while repeat // TODO This can't work, this tries to calculate the runtime condition at compile time
                returnList += this.returnCommands(world)
            }
        }

        return returnList
    }

    override fun returnColor(): Color {
        when(controllFlowType) {
            ControllFlowType.IF -> return Color(0xFF00BCD4)
            ControllFlowType.WHILE -> return Color(0xFF03A9F4)
            else -> return Color(0xFFFFFFFF) //"No Controllflow"
        }
    }

    override fun returnTextValue(): String {
        when(controllFlowType) {
            ControllFlowType.IF -> return ("IF (" + condition.returnTextValue() + ")") //Full text missing
            ControllFlowType.WHILE -> return ("WHILE (" + condition.returnTextValue() + ")") //Full text missing
            else -> return "No Controllflow"
        }
    }

    override fun size(): Int {
        return codeParts.size() + 2
    }

    override fun printAll() {
        println(returnTextValue() + " : " + index)

        codeParts.printAll()

        println("End " + controllFlowType.toString() + " : " + indexEnd)
    }

    override fun insertAt(goalIndex: Int, codeParts: CodeParts) {
        this.codeParts.insertAt(goalIndex, codeParts)
    }

    override fun updateIndex(lastIndex: Int) {
        index = lastIndex + 1

        codeParts.updateIndex(index)

        indexEnd = index + codeParts.size() + 1
    }
}