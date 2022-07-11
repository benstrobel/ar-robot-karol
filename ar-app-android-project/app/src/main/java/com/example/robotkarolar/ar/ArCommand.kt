package com.example.robotkarolar.ar

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

fun placeBlock(x: Int, y: Int, h: Int, blockType: BlockType): ArCommand {
    return ArCommand(ArCommandType.PLACEBLOCK, x, y, h, blockType)
}

fun removeBlock(x: Int, y: Int, h: Int): ArCommand {
    return ArCommand(ArCommandType.REMOVEBLOCK, x, y, h, null)
}

fun moveTo(x: Int, y: Int, h: Int): ArCommand {
    return ArCommand(ArCommandType.MOVETO, x, y, h, null)
}

fun rotateLeft(): ArCommand {
    return ArCommand(ArCommandType.ROTATELEFT, null, null, null, null)
}

fun rotateRight(): ArCommand {
    return ArCommand(ArCommandType.ROTATERIGHT, null, null, null, null)
}

fun lift(): ArCommand {
    return ArCommand(ArCommandType.LIFT,null, null, null, null)
}

fun end(): ArCommand {
    return ArCommand(ArCommandType.END, null, null, null, null)
}

@Parcelize
class ArCommand(var commandType: ArCommandType, var x: Int?, var y: Int?, var h: Int?, var blockType: BlockType?) : Parcelable {

}