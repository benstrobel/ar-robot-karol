package com.example.robotkarolar.karollogic_ramona.Parts

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color
import com.example.robotkarolar.karollogic_ramona.enums.CommandType
import com.example.robotkarolar.karollogic_ramona.karolWorld.World

class Chain: CodeParts {
    var code: MutableList<CodeParts>

    override var index = -1
    override var indexEnd = -1

    constructor(code: MutableList<CodeParts>) {
        this.code = code
    }

    //run Code and return list of commands executed
    override fun returnCommands(world: World): MutableList<CommandType> {
        var returnList: MutableList<CommandType> = mutableListOf()
        code.forEach{returnList += it.returnCommands(world)}

        return  returnList
    }

    override fun returnColor(): Color {
        return Color(0xFFFFFFFF)//"Chain shouldnt be called"
    }

    override fun returnTextValue(): String {
        return "Chain shouldnt be called"
    }

    override fun size(): Int {
        var returnVal = 0

        code.forEach {
            returnVal += it.size()
        }

        return returnVal
    }

    override fun printAll() {
        //println("Chaine : " + index)
        code.forEach {
            it.printAll()
        }
    }

    override fun insertAt(goalIndex: Int, codeParts: CodeParts) {
        var myIndex = 0
        if (goalIndex == index + 1) { //when adding first of chain
            if (myIndex <= code.size && myIndex >= 0) {
                code.add(myIndex, codeParts)

                //TODO:Remove testing
                println("Erste schleife called")
            }
        } else {
            for (i in 0 until code.size) {

                when (code[i]) {
                    is Command -> {
                        myIndex += 1

                        if (goalIndex == code[i].index + 1) {
                            if (myIndex <= code.size && myIndex >= 0) {
                                code.add(myIndex, codeParts)

                                //TODO:Remove testing
                                println("zweite schleife called: " + myIndex)

                                break
                            }
                        }
                    }

                    is Chain -> {
                        myIndex += 1
                        code[i].insertAt(goalIndex, codeParts)
                    }
                    is ControllFlow -> {
                        myIndex += 1
                        code[i].insertAt(goalIndex, codeParts)

                        if(goalIndex == code[i].indexEnd + 1) {
                            if (myIndex <= code.size && myIndex >= 0) {
                                code.add(myIndex, codeParts)
                            }
                        }
                    }
                }
            }
        }

        updateIndex(index)
    }

    /*override fun updateIndex(lastIndex: Int) {
        index = lastIndex
        var currentIndex = lastIndex

        code.forEach {
            it.updateIndex(currentIndex)

            currentIndex += it.size()
        }
    }*/

    override fun updateIndex(lastIndex: Int) {
        index = lastIndex
        indexEnd = lastIndex

        var currentIndex = lastIndex

        code.forEach {
            it.updateIndex(currentIndex)
            currentIndex += it.size()
        }
    }

}