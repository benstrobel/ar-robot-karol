package com.example.robotkarolar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.robotkarolar.ar.ArCommand
import com.example.robotkarolar.ar.ArCommandType
import com.example.robotkarolar.ar.BlockType
import com.example.robotkarolar.karollogic.Interpreter
import com.example.robotkarolar.karollogic.instructions.controlflow.CodeBlock
import com.example.robotkarolar.karollogic.instructions.statements.Noop
import com.example.robotkarolar.karollogic.world.World
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.ar.core.Config
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.ArNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.math.Scale
import io.github.sceneview.utils.setFullScreen

class ArActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var sceneView: ArSceneView
    private var karolCreated = false
    private lateinit var codeBlock: CodeBlock
    private lateinit var interpreter: Interpreter
    private lateinit var worldOrigin: ArNode
    private lateinit var placeButton: ExtendedFloatingActionButton
    private val allModelScale = 0.5f
    private var karolRotation = 0

    override fun onBackPressed() {
        finishAr()
    }

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

        placeButton = findViewById<ExtendedFloatingActionButton>(R.id.placeModelButton).apply {
            setOnClickListener{ debug() }
        }

        sceneView.planeRenderer.isShadowReceiver = false
        sceneView.onArSessionCreated = {
            val config = sceneView.arSession?.config
            config?.lightEstimationMode = Config.LightEstimationMode.ENVIRONMENTAL_HDR
            sceneView.arSession?.configure(config)
        }

        sceneView.onArFrame = {
            if(!karolCreated && it.isTrackingPlane) {
                sceneView.planeRenderer.isVisible = false
                worldOrigin = ArModelNode(PlacementMode.PLANE_HORIZONTAL, instantAnchor = true, followHitPosition = false)
                sceneView.addChild(worldOrigin)
                createKarol(0f,0f,0f, worldOrigin)
                karolCreated = true
            }
        }
    }

    private fun debug() {
        val karol = worldOrigin.children.first { it.name == "Karol" }
        when(karolRotation){
            0 -> {karol.rotation = Rotation(y = 90f); karolRotation = 1}
            1 -> {karol.rotation = Rotation(y = 180f); karolRotation = 2}
            2 -> {karol.rotation = Rotation(y = 270f); karolRotation = 3}
            3 -> {karol.rotation = Rotation(y = 0f); karolRotation = 0}
        }
        // createBlock(1, 0,0, BlockType.GRASS, worldOrigin)
    }

    fun runNext() {
        var command = interpreter.nextStep()

        if (command != null) {
            executeCommand(command)
        }
    }

    fun runAll() {
        var command = interpreter.nextStep()
        while (command != null && command.commandType != ArCommandType.END) {
            executeCommand(command)
            command = interpreter.nextStep()
        }
    }

    fun finishAr(){
        this.finish()
    }

    private fun executeCommand(command: ArCommand) {
        val x: Float = if (command.x != null) (command.x as Int).toFloat() else 0f
        val y: Float = if (command.y != null) (command.y as Int).toFloat() else 0f
        val h: Float = if (command.h != null) (command.h as Int).toFloat() else 0f
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

    private fun deleteBlock(x: Float, y: Float, h: Float, parent: ArNode = worldOrigin) {
        var blockExists = false
        parent.children.forEach{
            if (it.name == "Block$x$y$h") {
                blockExists = true
            }
        }

        if (blockExists) {
            val firstWithName = parent.children.first { it.name == "Block$x$y$h" }
            firstWithName.isVisible = false
            parent.removeChild(firstWithName)
        }
    }

    private fun moveKarol(x: Float, y: Float, h: Float, parent: ArNode = worldOrigin) { //TODO: Doesn't rereder yet
        parent.children.first { it.name == "Karol" }.position = Position(x.toFloat() , h.toFloat() , y. toFloat()) //TODO: TRANSFORM POSITION
    }

    private fun rotateKarol(arCommandType: ArCommandType, parent: ArNode = worldOrigin) {
        val karol = parent.children.first { it.name == "Karol" }

        when (arCommandType) {

            ArCommandType.ROTATERIGHT -> {
                when(karolRotation){
                    0 -> {karol.rotation = Rotation(y = 90f); karolRotation = 3}
                    1 -> {karol.rotation = Rotation(y = 180f); karolRotation = 0}
                    2 -> {karol.rotation = Rotation(y = 270f); karolRotation = 1}
                    3 -> {karol.rotation = Rotation(y = 0f); karolRotation = 2}
                }
            }

            ArCommandType.ROTATELEFT -> {
                when(karolRotation){
                    0 -> {karol.rotation = Rotation(y = 90f); karolRotation = 1}
                    1 -> {karol.rotation = Rotation(y = 180f); karolRotation = 2}
                    2 -> {karol.rotation = Rotation(y = 270f); karolRotation = 3}
                    3 -> {karol.rotation = Rotation(y = 0f); karolRotation = 0}
                }
            }

            else -> return
        }
    }

    private fun createKarol(x: Float, y: Float, h: Float, parent: ArNode = worldOrigin):ArModelNode? {
        //check if karol exists
        val context = this
        if (!karolCreated) {
            var karolNode = ArModelNode(
                PlacementMode.DISABLED,
                followHitPosition = false,
                instantAnchor = true,
            ).apply {
                loadModelAsync(
                    context = context,
                    lifecycle = lifecycle,
                    glbFileLocation = "model_steve/steveScaled.glb",
                    autoScale = false,
                    centerOrigin = Position(0f,0f,0f)
                )
                position = Position(x, y, h)
                rotation = Rotation(x= 0f, y = 0f, z = 0f)
                name = "Karol"
                scale = Scale(allModelScale)
            }
            parent.addChild(karolNode)
            return karolNode
        }
        return null
    }

    private fun createBlock(x: Float, y: Float, h: Float, blockType: BlockType, parent: ArNode = worldOrigin):ArModelNode? {
        val context = this
        val modelString = when (blockType) {
            BlockType.GRASS -> "model_grasblock/gras.glb"
            BlockType.STONE -> "model_stoneblock/stone.glb"
            BlockType.WATER -> "model_waterblock/water.glb"
        }

        //if no block at this coords exists
        if (parent.children.firstOrNull() { it.name == "Block$x$y$h" } == null) {
            var blockNode = ArModelNode(
                PlacementMode.DISABLED,
                followHitPosition = false,
                instantAnchor = true,
            ).apply {
                loadModelAsync(
                    context = context,
                    lifecycle = lifecycle,
                    glbFileLocation = modelString,
                    autoScale = false,
                    centerOrigin = Position(0f,0f,0f)
                )
                name = "Block$x$y$h"
                scale = Scale(allModelScale)
                position = Position(x, y, h)
                rotation = Rotation(y = 0f)
            }
            parent.apply {
                addChild(blockNode)
            }
            return blockNode
        }
        return null
    }
}