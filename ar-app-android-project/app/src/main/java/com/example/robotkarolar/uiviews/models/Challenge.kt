package com.example.robotkarolar.uiviews.models

import com.example.robotkarolar.karollogic.world.World

class Challenge {
    fun createWorld(challengeId: Int): World {
        return when(challengeId){
            1 -> challenge1()
            2 -> challenge2()
            else -> World()
        }
    }

    fun challenge1(): World {
        var worldChallenge1 = World()
        val karol = worldChallenge1.addEntity(0,0)
        worldChallenge1.selectedEntity = karol

        worldChallenge1.step()
        worldChallenge1.step()
        worldChallenge1.place()
        worldChallenge1.place()
        worldChallenge1.step()

        return worldChallenge1
    }

    fun challenge2(): World {
        var worldChallenge2 = World()
        val karol = worldChallenge2.addEntity(0,0)
        worldChallenge2.selectedEntity = karol

        worldChallenge2.place()
        worldChallenge2.step()
        worldChallenge2.place()
        worldChallenge2.rightTurn()
        worldChallenge2.step()
        worldChallenge2.step()

        return worldChallenge2
    }
}