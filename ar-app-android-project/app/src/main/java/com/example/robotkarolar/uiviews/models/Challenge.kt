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
        return World()
    }

    fun challenge2(): World {
        return World()
    }
}