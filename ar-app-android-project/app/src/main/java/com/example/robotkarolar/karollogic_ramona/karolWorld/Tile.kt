package com.example.robotkarolar.karollogic_ramona.karolWorld

import com.example.robotkarolar.karollogic_ramona.enums.BlockType

class Tile {
    var blocks: MutableList<BlockType> = mutableListOf()

    fun addBlock(blockType: BlockType) {
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