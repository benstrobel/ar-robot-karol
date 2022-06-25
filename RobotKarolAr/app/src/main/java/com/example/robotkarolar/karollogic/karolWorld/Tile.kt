package com.example.robotkarolar.karollogic.karolWorld

import com.example.robotkarolar.karollogic.enums.BlockTyp

class Tile {
    var blocks: MutableList<BlockTyp> = mutableListOf()

    fun addBlock(blockType: BlockTyp) {
        blocks.add(blockType)
    }

    fun removeBlock() {
        if (!blocks.isEmpty()) {
            blocks.removeLast()
        } else {
            println("Here were no Blocks")
        }
    }

    fun isEmpty(): Boolean {
        return blocks.isEmpty()
    }
}