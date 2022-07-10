package com.example.robotkarolar.ar

fun placeBlock(x: Int, y: Int, h: Int, blockType: BlockType): ArCommand {
    return ArCommand(ArCommandType.PLACEBLOCK, x, y, h, blockType)
}

fun removeBlock(x: Int, y: Int, h: Int): ArCommand {
    return ArCommand(ArCommandType.REMOVEBLOCK, x, y, h)
}

fun moveTo(x: Int, y: Int, h: Int): ArCommand {
    return ArCommand(ArCommandType.MOVETO, x, y, h)
}

fun rotateLeft(): ArCommand {
    return ArCommand(ArCommandType.ROTATELEFT)
}

fun rotateRight(): ArCommand {
    return ArCommand(ArCommandType.ROTATERIGHT)
}

fun lift(): ArCommand {
    return ArCommand(ArCommandType.LIFT)
}

fun end(): ArCommand {
    return ArCommand(ArCommandType.END)
}

class ArCommand internal constructor(commandType: ArCommandType, x: Int? = null, y: Int? = null, h: Int? = null, blockType: BlockType? = null) {

}