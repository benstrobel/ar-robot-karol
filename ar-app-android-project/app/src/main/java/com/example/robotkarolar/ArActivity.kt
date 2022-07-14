package com.example.robotkarolar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
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
import com.google.ar.sceneform.math.Vector3
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
    private val allModelScale = 0.25f
    private var karolRotation = 2
    private val blockSize: Vector3 = Vector3(0.37712634f*allModelScale, 0.37712651f*allModelScale, 0.37712651f*allModelScale)
    lateinit var cursorNode: ArModelNode
    private var world = World()

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        startActivity(intent)
    }

    override fun onResume() {
        world = World()
        val karol = world.addEntity(0,0)
        world.selectedEntity = karol
        interpreter = Interpreter(codeBlock, world)
        interpreter.reset()
        if(this::worldOrigin.isInitialized) {
            val karol = worldOrigin.children.first { it.name == "Karol" }
            karol.isVisible = false
            worldOrigin.removeChild(karol)
            karolCreated = false
            worldOrigin.children.forEach {
                if(it.name?.startsWith("Block") == true) {
                    it.isVisible = false
                    worldOrigin.removeChild(it)
                }
            }
            createKarol(0,0,0, worldOrigin)
        }
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFullScreen(
            findViewById(R.id.rootView),
            fullScreen = true,
            hideSystemBars = false,
            fitsSystemWindows = true
        )

        val bundle = intent.extras
        if (bundle != null) {
            val bundleCodeBlock = bundle.getParcelable<CodeBlock>("codeBlock")
            codeBlock = if (bundleCodeBlock != null) bundleCodeBlock as CodeBlock else CodeBlock(mutableListOf(Noop()))
        }

        val karol = world.addEntity(0,0)
        world.selectedEntity = karol
        interpreter = Interpreter(codeBlock, world)

        sceneView = findViewById(R.id.sceneView)

        placeButton = findViewById<ExtendedFloatingActionButton>(R.id.placeModelButton).apply {
            setOnClickListener{
                placeBoard()
            }
        }

        sceneView.planeRenderer.isShadowReceiver = false
        sceneView.onArSessionCreated = {
            val config = sceneView.arSession?.config
            config?.lightEstimationMode = Config.LightEstimationMode.ENVIRONMENTAL_HDR
            sceneView.arSession?.configure(config)
        }

        val context = this
        sceneView.planeRenderer.isVisible = false
        cursorNode = ArModelNode(PlacementMode.PLANE_HORIZONTAL, followHitPosition = true).apply {
            loadModelAsync(
                context = context,
                lifecycle = lifecycle,
                glbFileLocation = "sceneview/models/cursor.glb",
                autoScale = false
            )
        }
        sceneView.addChild(cursorNode)
    }

    private fun placeBoard() {
        if(!karolCreated) {
            worldOrigin = ArModelNode(
                PlacementMode.DISABLED,
                followHitPosition = false,
                instantAnchor = true,
            )
            worldOrigin.apply {

                val xOffSet = if (sceneView.camera.position.x > cursorNode.position.x) cursorNode.position.x + (world.xSize * blockSize.x / 2) else cursorNode.position.x - (world.xSize * blockSize.x / 2)
                val zOffSet = if (sceneView.camera.position.z > cursorNode.position.z) cursorNode.position.z + (world.ySize * blockSize.z / 2) else cursorNode.position.z - (world.ySize * blockSize.z / 2)
                position = Position(xOffSet, cursorNode.position.y, zOffSet)
                rotation = Rotation(y = sceneView.camera.rotation.y)
            }
            cursorNode.isVisible = false
            sceneView.removeChild(cursorNode)
            sceneView.addChild(worldOrigin)
            createFloor(world, worldOrigin)
            createKarol(0,0,0, worldOrigin)
            karolCreated = true

            //Hide Place Button
            findViewById<ExtendedFloatingActionButton>(R.id.placeModelButton).visibility = View.GONE
            findViewById<LinearLayout>(R.id.buttonRow).visibility = View.VISIBLE
        }
    }

    //UIFunctions
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
        this.onBackPressed()
        //this.finish()
    }

    //Commands
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

    private fun deleteBlock(x: Int, y: Int, h: Int, parent: ArNode = worldOrigin) {
        val block = parent.children.firstOrNull {it.name == "Block$x$y$h"}
        if(block != null){
            block.isVisible = false
            parent.removeChild(block)
        }
    }

    private fun moveKarol(x: Int, y: Int, h: Int, parent: ArNode = worldOrigin) {
        parent.children.first { it.name == "Karol" }.position = Position(blockSize.x*x, blockSize.y*h, -blockSize.z*y)
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

    //Load Models
    private fun createKarol(x: Int, y: Int, h: Int, parent: ArNode = worldOrigin):ArModelNode? {
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
                    glbFileLocation = "model_steve/steveDouble2.glb",
                    autoScale = false,
                    centerOrigin = Position(0f,-1f,0f)
                )
                position = Position(blockSize.x*x, blockSize.y*h, -blockSize.z*y)
                rotation = Rotation(x= 0f, y = 180f, z = 0f)
                name = "Karol"
                scale = Scale(allModelScale)
            }
            parent.addChild(karolNode)
            return karolNode
        }
        return null
    }

    private fun createFloor(world: World, parent: ArNode = worldOrigin) {
        var context = this
        for(y in 0..world.ySize) {
            for(x in 0..world.xSize) {
                val node = ArModelNode(
                    PlacementMode.DISABLED,
                    followHitPosition = false,
                    instantAnchor = true,
                ).apply {
                    loadModelAsync(
                        context = context,
                        lifecycle = lifecycle,
                        glbFileLocation = "model_floor/floorTest.glb",
                        autoScale = false,
                        centerOrigin = Position(0f,1f,0f)
                    )
                    scale = Scale(allModelScale)
                    position = Position(blockSize.x*x, 0f, -blockSize.z*y)
                    rotation = Rotation(y = 0f)
                }
                parent.addChild(node)
            }
        }
    }

    private fun createBlock(x: Int, y: Int, h: Int, blockType: BlockType, parent: ArNode = worldOrigin):ArModelNode? {
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
                    centerOrigin = Position(0f,-1f,0f)
                )
                name = "Block$x$y$h"
                scale = Scale(allModelScale)
                position = Position(blockSize.x*x, blockSize.y*h, -blockSize.z*y)
                rotation = Rotation(y = 0f)
            }
            parent.addChild(blockNode)
            return blockNode
        }
        return null
    }
}