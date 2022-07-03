package com.example.robotkarolar.karollogic_ramona.karolWorld

import com.example.robotkarolar.karollogic_ramona.enums.BlockType
import com.example.robotkarolar.karollogic_ramona.enums.CommandType
import com.example.robotkarolar.karollogic_ramona.enums.DirectionType

class World {
    var roboterX: Int = 0
    var roboterY: Int = 0
    var roboterRotation: DirectionType = DirectionType.SOUTH

    var matrix: Array<Array<Tile>>

    constructor() {
        //Array of size 10
        matrix = arrayOf<Array<Tile>>()

        for (i in 0..9) {
            var array = arrayOf<Tile>()
            for (j in 0..9) {
                array += Tile()
            }
            matrix += array
        }

        printWorld()
    }

    fun command(command: CommandType) {
        when(command) {
            CommandType.STEP -> step()
            CommandType.TURNLEFT -> turnLeft()
            CommandType.TURNRIGHT -> turnRight()
            CommandType.TURN -> turn()
            CommandType.PLACEGRAS -> place(BlockType.GRAS)
            CommandType.PLACESTONE -> place(BlockType.STONE)
            CommandType.PLACEWATER -> place(BlockType.WATER)
            CommandType.REMOVE -> remove()
            else -> print("Unknown Command")
        }

        printWorld()
    }

    fun printWorld() {
        for (array in matrix) {
            for (value in array) {
                print(value.blocks.size)
            }
            println()
        }

        println()
        println("Karol X: $roboterX Y: $roboterY")
    }

    private fun step() {
        when(roboterRotation) {
            DirectionType.SOUTH -> {roboterY += 1}
            DirectionType.NORTH -> {roboterY -= 1}
            DirectionType.EAST -> {roboterX +=1}
            DirectionType.WEST -> {roboterX -= 1}
            else -> print("No Direction")
        }
    }

    private fun turnLeft() {
        when(roboterRotation) {
            DirectionType.SOUTH -> {roboterRotation = DirectionType.EAST}
            DirectionType.NORTH -> {roboterRotation = DirectionType.WEST}
            DirectionType.EAST -> {roboterRotation = DirectionType.NORTH}
            DirectionType.WEST -> {roboterRotation = DirectionType.SOUTH}
            else -> print("No Direction")
        }
    }

    private fun turnRight() {
        when(roboterRotation) {
            DirectionType.SOUTH -> {roboterRotation = DirectionType.WEST}
            DirectionType.NORTH -> {roboterRotation = DirectionType.EAST}
            DirectionType.EAST -> {roboterRotation = DirectionType.SOUTH}
            DirectionType.WEST -> {roboterRotation = DirectionType.NORTH}
            else -> print("No Direction")
        }
    }

    private fun turn() {
        when(roboterRotation) {
            DirectionType.SOUTH -> {roboterRotation = DirectionType.NORTH}
            DirectionType.NORTH -> {roboterRotation = DirectionType.SOUTH}
            DirectionType.EAST -> {roboterRotation = DirectionType.WEST}
            DirectionType.WEST -> {roboterRotation = DirectionType.EAST}
            else -> print("No Direction")
        }
    }

    private fun place(blockType: BlockType) {
        //check if karol is in array
        if (roboterX > -1 && roboterX < 10 && roboterY > -1 && roboterY < 10 ) {
            matrix[roboterY][roboterX].addBlock(blockType)
        } else {
            println("Karol is not in the field")
        }
    }

    private fun remove() {
        //check if karol is in array
        if (roboterX > -1 && roboterX < 10 && roboterY > -1 && roboterY < 10 ) {
            matrix[roboterY][roboterX].removeBlock()
        } else {
            println("Karol is not in the field")
        }
    }

    fun isBlock(): Boolean {
        var returnValue = true

        when(roboterRotation) {
            DirectionType.EAST -> {returnValue = !matrix[roboterY][roboterX+1].isEmpty()}
            DirectionType.NORTH -> {returnValue = !matrix[roboterY-1][roboterX].isEmpty()}
            DirectionType.SOUTH -> {returnValue = !matrix[roboterY+1][roboterX].isEmpty()}
            DirectionType.WEST -> {returnValue = !matrix[roboterY][roboterX-1].isEmpty()}
            else -> println("block problems")
        }

        return returnValue
    }

    fun isBorder(): Boolean {
        var returnValue = true

        when(roboterRotation) {
            DirectionType.EAST -> {returnValue = (roboterX == 9)}
            DirectionType.NORTH -> {returnValue = (roboterY == 0)}
            DirectionType.SOUTH -> {returnValue = (roboterY == 9)}
            DirectionType.WEST -> {returnValue = (roboterX == 0)}
            else -> println("boarder problems")
        }

        return returnValue
    }

    fun isDirection(directions: DirectionType): Boolean {
        return (roboterRotation == directions)
    }
}