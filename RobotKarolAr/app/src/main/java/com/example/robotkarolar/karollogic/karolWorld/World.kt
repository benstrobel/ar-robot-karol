package com.example.robotkarolar.karollogic.karolWorld

import com.example.robotkarolar.karollogic.enums.BlockTyp
import com.example.robotkarolar.karollogic.enums.CommandType
import com.example.robotkarolar.karollogic.enums.DirectionTyp

class World {
    var roboterX: Int = 0
    var roboterY: Int = 0
    var roboterRotation: DirectionTyp = DirectionTyp.SOUTH

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
            CommandType.PLACEGRAS -> place(BlockTyp.GRAS)
            CommandType.PLACESTONE -> place(BlockTyp.STONE)
            CommandType.PLACEWATER -> place(BlockTyp.WATER)
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
            DirectionTyp.SOUTH -> {roboterY += 1}
            DirectionTyp.NORTH -> {roboterY -= 1}
            DirectionTyp.EAST -> {roboterX +=1}
            DirectionTyp.WEST -> {roboterX -= 1}
            else -> print("No Direction")
        }
    }

    private fun turnLeft() {
        when(roboterRotation) {
            DirectionTyp.SOUTH -> {roboterRotation = DirectionTyp.EAST}
            DirectionTyp.NORTH -> {roboterRotation = DirectionTyp.WEST}
            DirectionTyp.EAST -> {roboterRotation = DirectionTyp.NORTH}
            DirectionTyp.WEST -> {roboterRotation = DirectionTyp.SOUTH}
            else -> print("No Direction")
        }
    }

    private fun turnRight() {
        when(roboterRotation) {
            DirectionTyp.SOUTH -> {roboterRotation = DirectionTyp.WEST}
            DirectionTyp.NORTH -> {roboterRotation = DirectionTyp.EAST}
            DirectionTyp.EAST -> {roboterRotation = DirectionTyp.SOUTH}
            DirectionTyp.WEST -> {roboterRotation = DirectionTyp.NORTH}
            else -> print("No Direction")
        }
    }

    private fun turn() {
        when(roboterRotation) {
            DirectionTyp.SOUTH -> {roboterRotation = DirectionTyp.NORTH}
            DirectionTyp.NORTH -> {roboterRotation = DirectionTyp.SOUTH}
            DirectionTyp.EAST -> {roboterRotation = DirectionTyp.WEST}
            DirectionTyp.WEST -> {roboterRotation = DirectionTyp.EAST}
            else -> print("No Direction")
        }
    }

    private fun place(blockTyp: BlockTyp) {
        //check if karol is in array
        if (roboterX > -1 && roboterX < 10 && roboterY > -1 && roboterY < 10 ) {
            matrix[roboterY][roboterX].addBlock(blockTyp)
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
            DirectionTyp.EAST -> {returnValue = !matrix[roboterY][roboterX+1].isEmpty()}
            DirectionTyp.NORTH -> {returnValue = !matrix[roboterY-1][roboterX].isEmpty()}
            DirectionTyp.SOUTH -> {returnValue = !matrix[roboterY+1][roboterX].isEmpty()}
            DirectionTyp.WEST -> {returnValue = !matrix[roboterY][roboterX-1].isEmpty()}
            else -> println("block problems")
        }

        return returnValue
    }

    fun isBorder(): Boolean {
        var returnValue = true

        when(roboterRotation) {
            DirectionTyp.EAST -> {returnValue = (roboterX == 9)}
            DirectionTyp.NORTH -> {returnValue = (roboterY == 0)}
            DirectionTyp.SOUTH -> {returnValue = (roboterY == 9)}
            DirectionTyp.WEST -> {returnValue = (roboterX == 0)}
            else -> println("boarder problems")
        }

        return returnValue
    }

    fun isDirection(directions: DirectionTyp): Boolean {
        return (roboterRotation == directions)
    }
}