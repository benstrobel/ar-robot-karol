package com.example.robotkarolar

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.robotkarolar.ar.ArCommand
import com.example.robotkarolar.ar.ArCommandType
import com.example.robotkarolar.ar.BlockType
import com.example.robotkarolar.ar.placeBlock
import com.example.robotkarolar.karollogic.Interpreter
import com.example.robotkarolar.karollogic.instructions.controlflow.CodeBlock
import com.example.robotkarolar.karollogic.instructions.statements.Noop
import com.example.robotkarolar.karollogic.world.World
import com.google.ar.core.Anchor
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.EditableTransform
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.utils.setFullScreen
import kotlinx.coroutines.delay

class ArActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var sceneView: ArSceneView
    //private var arrayCommand: ArrayList<ArCommand>? = null
    //private var indexInCommands: Int = 0
    private var karolCreated = false
    private lateinit var codeBlock: CodeBlock
    private lateinit var interpreter: Interpreter
    private lateinit var firstAnchor: Anchor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFullScreen(
            findViewById(R.id.rootView),
            fullScreen = true,
            hideSystemBars = false,
            fitsSystemWindows = false
        )

        val bundle = intent.extras
        if (bundle != null) {
            val bundleCodeBlock = bundle.getParcelable<CodeBlock>("codeBlock")
            codeBlock = if (bundleCodeBlock != null) bundleCodeBlock as CodeBlock else CodeBlock(mutableListOf(Noop()))
        }

        val world = World()
        val karol = world.addEntity(0,0)
        world.selectedEntity = karol
        interpreter = Interpreter(codeBlock, world)

        sceneView = findViewById(R.id.sceneView)

        sceneView.onArFrame = {
            if(!karolCreated && it.isTrackingPlane) {
                createKarol(0,0,0)
                karolCreated = true
            }
        }
    }

    private fun debug() {
        createBlock(1, 0,0, BlockType.GRASS)
    }

    fun runNext(v: View) {
        var command = interpreter.nextStep()

        if (command != null) {
            executeCommand(command)
        }
    }

    fun runAll(v: View) {
        var command = interpreter.nextStep()
        while (command != null && command.commandType != ArCommandType.END) {
            executeCommand(command)
            command = interpreter.nextStep()
        }
    }

    fun finishAr(v: View){
        this.finish()
    }

    private fun executeCommand(command: ArCommand) {
        val x: Int = if (command.x != null) command.x as Int else 0
        val y: Int = if (command.y != null) command.y as Int else 0
        val h: Int = if (command.h != null) command.h as Int else 0
        val blockType: BlockType = if (command.blockType != null) command.blockType as BlockType else BlockType.GRASS


        when(command.commandType) {
            ArCommandType.REMOVEBLOCK -> deleteBlock(x, y, h)
            ArCommandType.MOVETO -> moveKarol(x, y, h)
            ArCommandType.PLACEBLOCK -> createBlock(x, y, h, blockType)
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
        if (!karolCreated) {
            var karolNode = ArModelNode(
                context = this,
                lifecycle = lifecycle,
                modelFileLocation = "model_steve/steveScaled.glb",
                autoScale = false,
                // Place the model origin at horizontal and vertical middle
                centerOrigin = Position(0.0f, 0.0f , 0.0f)
            ).apply {
                name = "Karol"
                instantAnchor = true
                position = Position(x.toFloat(), y.toFloat(), h.toFloat())
            }
            sceneView.apply {
                addChild(karolNode)
                planeRenderer.isVisible = false
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
                //autoAnimate = true,
                autoScale = false,
                // Place the model origin at the bottom center
                centerOrigin = Position(0.0f, 0.0f , 0.0f)
            ).apply {
                name = "Block$x$y$h"

                instantAnchor = true
                position = Position(x.toFloat(), y.toFloat(), h.toFloat())
            }
            sceneView.apply {
                addChild(blockNode)
            }
        }
    }
}