package com.example.robotkarolar

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.robotkarolar.ar.ArCommand
import com.example.robotkarolar.ar.ArCommandType
import com.example.robotkarolar.ar.BlockType
import com.example.robotkarolar.ar.placeBlock
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.EditableTransform
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.utils.setFullScreen

class ArActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var sceneView: ArSceneView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFullScreen(
            findViewById(R.id.rootView),
            fullScreen = true,
            hideSystemBars = false,
            fitsSystemWindows = false
        )

        sceneView = findViewById(R.id.sceneView)

        //TODO: Testing Remove later
        createKarol(0, 0, 0)
        //createBlock(0,0,0, BlockType.GRASS)
        createBlock(1,0,0, BlockType.WATER)
        createBlock(2,0,0, BlockType.WATER)
        createBlock(2,0,1, BlockType.STONE)
        deleteBlock(1,0,0)
        createBlock(1,0,0, BlockType.WATER)
        createBlock(2,1,0, BlockType.GRASS)

        rotateKarol(ArCommandType.ROTATELEFT) //doesnt rerender yet
    }

    fun runNext() {

    }

    fun runAll() {

    }

    private fun executeCommand(command: ArCommand) {
        val x: Int = if (command.x != null) command.x as Int else 0
        val y: Int = if (command.y != null) command.y as Int else 0
        val h: Int = if (command.h != null) command.h as Int else 0
        val blockType: BlockType = if (command.blockType != null) command.blockType as BlockType else BlockType.GRASS


        when(command.commandType) {
            ArCommandType.REMOVEBLOCK -> deleteBlock(x, y, h)
            ArCommandType.MOVETO -> moveKarol(x, y, h)
            ArCommandType.PLACEBLOCK -> placeBlock(x, y, h, blockType)
            ArCommandType.ROTATELEFT -> rotateKarol(command.commandType)
            ArCommandType.ROTATERIGHT -> rotateKarol(command.commandType)
            else -> return
        }
    }

    private fun deleteBlock(x: Int, y: Int, h: Int) {
        var blockExists = false
        sceneView.children.forEach{
            if (it.name == "Block$x$y$h") {
                blockExists = true
            }
        }

        if (blockExists) {
            val firstWithName = sceneView.children.first { it.name == "Block$x$y$h" }
            firstWithName.isVisible = false
            sceneView.removeChild(firstWithName)
        }
    }

    private fun moveKarol(x: Int, y: Int, h: Int) { //TODO: Doesn't rereder yet
        sceneView.children.first { it.name == "Karol" }.position = Position(x.toFloat() , h.toFloat() , y. toFloat()) //TODO: TRANSFORM POSITION
    }

    private fun rotateKarol(arCommandType: ArCommandType) { //TODO: Doesn't rereder yet
        when (arCommandType) {

            ArCommandType.ROTATERIGHT -> {
                sceneView.children.first { it.name == "Karol" }.rotation = Rotation(y = 180.0f) //TODO: Change rotation due to North, East, West, South
            }

            ArCommandType.ROTATELEFT -> {
                sceneView.children.first { it.name == "Karol" }.rotation = Rotation(y = 180.0f) //TODO: Change rotation due to North, East, West, South
            }

            else -> return
        }
    }

    private fun createKarol(x: Int, y: Int, h: Int) {
        //check if karol exists
        var karolExists = false
        sceneView.children.forEach{
            if (it.name == "Karol") {
                karolExists = true
            }
        }

        if (!karolExists) {
            var blockNode = ArModelNode(
                context = this,
                lifecycle = lifecycle,
                modelFileLocation = "model_steve/steveScaled.glb",
                autoAnimate = true,
                autoScale = false,
                // Place the model origin at the bottom center
                centerOrigin = Position(x = x.toFloat(), y = h.toFloat(), z = y.toFloat()) //TODO: TRANSFORM POSITION
            ).apply {
                name = "Karol"
                placementMode = PlacementMode.BEST_AVAILABLE
                /*onPoseChanged = { node, _ ->
                    actionButton.isGone = !node.isTracking
                }*/
                editableTransforms = EditableTransform.ALL
            }
            sceneView.apply {
                addChild(blockNode)
                // Select the model node by default (the model node is also selected on tap)
                //gestureDetector.selectedNode = modelNode
            }
        }
    }

    private fun createBlock(x: Int, y: Int, h: Int, blockType: BlockType) {
        var modelString: String
        when (blockType) {
            BlockType.GRASS -> modelString = "model_grasblock/gras.glb"
            BlockType.STONE -> modelString = "model_stoneblock/stone.glb"
            BlockType.WATER -> modelString = "model_waterblock/water.glb"
        }

        //check if block exists
        var blockExists = false
        sceneView.children.forEach{
            if (it.name == "Block$x$y$h") {
                blockExists = true
            }
        }

        if (!blockExists) {
            var blockNode = ArModelNode(
                context = this,
                lifecycle = lifecycle,
                modelFileLocation = modelString,
                autoAnimate = true,
                autoScale = false,
                // Place the model origin at the bottom center
                centerOrigin = Position(x = -2 * x.toFloat(), y = -2 * h.toFloat(), z = -2 * y.toFloat())
            ).apply {
                name = "Block$x$y$h"
                placementMode = PlacementMode.BEST_AVAILABLE
                /*onPoseChanged = { node, _ ->
                    actionButton.isGone = !node.isTracking
                }*/
                editableTransforms = EditableTransform.ALL
            }
            sceneView.apply {
                addChild(blockNode)
                // Select the model node by default (the model node is also selected on tap)
                //gestureDetector.selectedNode = modelNode
            }
        }
    }

    fun finishAr(v: View){
        this.finish()
    }
}