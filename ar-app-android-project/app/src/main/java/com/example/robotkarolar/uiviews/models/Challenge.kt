package com.example.robotkarolar.uiviews.models

import com.example.robotkarolar.karollogic.world.World

class Challenge {
    fun createWorld(challengeId: Int): World {
        return when(challengeId){
            1 -> challenge1()
            2 -> challenge2()
            3 -> challenge3()
            4 -> challenge4()
            else -> World()
        }
    }

    private fun challenge1(): World {
        var world = World()
        val karol = world.addEntity(0,0)
        world.selectedEntity = karol

        world.placeStone()
        world.step()
        world.placeStone()
        world.placeStone()
        world.step()
        world.placeStone()
        world.placeStone()
        world.placeStone()

        return world
    }

    private fun challenge2(): World {
        var world = World()
        val karol = world.addEntity(0,0)
        world.selectedEntity = karol

        world.step()
        world.step()
        world.placeStone()
        world.placeStone()
        world.placeStone()
        world.placeStone()
        world.rightTurn()
        world.step()
        world.leftTurn()
        world.placeStone()
        world.rightTurn()
        world.step()
        world.leftTurn()
        world.placeStone()
        world.placeStone()
        world.placeStone()
        world.placeStone()
        world.rightTurn()
        world.step()
        world.step()
        world.leftTurn()
        world.placeStone()
        world.placeStone()
        world.placeStone()
        world.placeStone()

        return world
    }

    private fun challenge3(): World {
        var world = World()
        val karol = world.addEntity(0,0)
        world.selectedEntity = karol

        while(!world.isBorder) {
            world.placeStone()
            world.step()
        }

        return world
    }

    private fun challenge4(): World {
        var world = World()
        val karol = world.addEntity(0,0)
        world.selectedEntity = karol

        while(!world.isBlock) {
            while(!world.isBorder) {
                world.placeStone()
                world.step()
            }
            world.rightTurn()
        }

        return world
    }
}