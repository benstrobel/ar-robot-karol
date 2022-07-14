package com.example.robotkarolar

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.robotkarolar.ar.ArCommand
import com.example.robotkarolar.ar.ArCommandType
import com.example.robotkarolar.ar.BlockType
import com.example.robotkarolar.karollogic.Interpreter
import com.example.robotkarolar.karollogic.instructions.controlflow.CodeBlock
import com.example.robotkarolar.karollogic.instructions.statements.Noop
import com.example.robotkarolar.karollogic.world.World
import com.example.robotkarolar.uiviews.models.Challenge
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.ar.core.Config
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import dev.romainguy.kotlin.math.pow
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.ArNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.math.Scale
import io.github.sceneview.utils.setFullScreen
import kotlin.math.sqrt

class ArActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var sceneView: ArSceneView
    private var karolCreated = false
    private lateinit var codeBlock: CodeBlock
    private lateinit var interpreter: Interpreter
    private lateinit var worldOrigin: ArNode
    private lateinit var placeButton: ExtendedFloatingActionButton
    private val baseModelScale = 0.15f
    private var currentModelScale = baseModelScale
    private var karolRotation = 2
    private var blockSize: Vector3 = Vector3(0.37712634f*baseModelScale, 0.37712651f*baseModelScale, 0.37712651f*baseModelScale)
    lateinit var cursorNode: ArModelNode
    private var world = World()
    private val handler = Handler(Looper.getMainLooper())
    private var stopRunAll = false
    private var runningAll = false

    //TODO: Challenge
    private var hasChallenge = false
    private var challengeWorld = World()

    override fun onBackPressed() {
        removeBoard(false)
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        startActivity(intent)
    }

    fun finishAr(v: View){
        this.onBackPressed()
        //this.finish()
    }

    override fun onResume() {
        resetWorldAndInterpreter()
        if(this::worldOrigin.isInitialized) {
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

            val challengeNumber = bundle.getInt("challengeNumber")
            hasChallenge = if (challengeNumber != null) (challengeNumber != -1) else false
            if (hasChallenge) {
                challengeWorld = Challenge().createWorld(challengeNumber)
            }
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

    private fun resetWorldAndInterpreter() {
        world = World()
        val karol = world.addEntity(0,0)
        world.selectedEntity = karol
        karolRotation = 2
        interpreter = Interpreter(codeBlock, world)
        interpreter.reset()
    }

    private fun removeBoard(removeFloor: Boolean) {
        if(this::worldOrigin.isInitialized) {
            val karol = worldOrigin.children.first { it.name == "Karol" }
            karol.isVisible = false
            worldOrigin.removeChild(karol)
            karolCreated = false
            worldOrigin.children.forEach {
                if(it.name?.startsWith("Block") == true) {
                    it.isVisible = false
                    worldOrigin.removeChild(it)
                } else if (removeFloor && it.name?.equals("FloorTile") == true) {
                    it.isVisible = false
                    worldOrigin.removeChild(it)
                }
            }
        }
    }

    private fun placeBoard() {
        if(!karolCreated) {
            worldOrigin = ArModelNode(
                PlacementMode.DISABLED,
                followHitPosition = false,
                instantAnchor = true,
            )
            worldOrigin.apply {
                val distanceInM = sqrt(pow((cursorNode.position.x - sceneView.camera.position.x), 2F) + pow((cursorNode.position.y - sceneView.camera.position.y), 2F) + pow((cursorNode.position.z - sceneView.camera.position.z), 2F))
                currentModelScale = baseModelScale * distanceInM
                blockSize = Vector3(0.37712634f*currentModelScale, 0.37712651f*currentModelScale, 0.37712651f*currentModelScale)
                // TODO Center on cursor, Maybe use Quaternion.rotateVector()
                position = Position(cursorNode.position.x, cursorNode.position.y, cursorNode.position.z)
                rotation = Rotation(y = sceneView.camera.rotation.y)
            }
            cursorNode.isVisible = false
            sceneView.removeChild(cursorNode)
            cursorNode.destroy()
            sceneView.addChild(worldOrigin)
            createFloor(world, worldOrigin)
            createKarol(0,0,0, worldOrigin)
            karolCreated = true

            //Hide Place Button
            findViewById<ExtendedFloatingActionButton>(R.id.placeModelButton).visibility = View.GONE
            findViewById<LinearLayout>(R.id.buttonRow).visibility = View.VISIBLE
            findViewById<ImageButton>(R.id.moveBoardButton).visibility = View.VISIBLE
        }
    }

    //UIFunctions
    fun runNext(v: View) {
        var command = interpreter.nextStep()

        if (command != null) {
            executeCommand(command)
        }
    }

    fun moveBoard(v: View) {
        val context = this
        findViewById<LinearLayout>(R.id.buttonRow).visibility = View.GONE
        findViewById<ImageButton>(R.id.moveBoardButton).visibility = View.GONE
        removeBoard(true)
        resetWorldAndInterpreter()
        cursorNode = ArModelNode(PlacementMode.PLANE_HORIZONTAL, followHitPosition = true).apply {
            loadModelAsync(
                context = context,
                lifecycle = lifecycle,
                glbFileLocation = "sceneview/models/cursor.glb",
                autoScale = false
            )
        }
        sceneView.addChild(cursorNode)
        findViewById<ExtendedFloatingActionButton>(R.id.placeModelButton).visibility = View.VISIBLE
    }

    fun runAllOrPause(v: View) {
        if(runningAll) {
            pause()
        } else {
            runAll()
        }
    }

    private fun runAll() {
        val command = interpreter.nextStep()
        if(command != null && command.commandType != ArCommandType.END) {
            runningAll = true
            findViewById<ImageButton>(R.id.buttonAllOrPause).setImageResource(R.drawable.pause)
            if(stopRunAll) {
                stopRunAll = false
                runningAll = false
                findViewById<ImageButton>(R.id.buttonAllOrPause).setImageResource(R.drawable.run_all)
                return
            }
            executeCommand(command)
            handler.postDelayed({runAll()}, 300)
        } else {
            runningAll = false
            findViewById<ImageButton>(R.id.buttonAllOrPause).setImageResource(R.drawable.run_all)
        }
    }

    private fun pause() {
        stopRunAll = true
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
                if(karolRotation == 0) {
                    karolRotation = 3
                } else {
                    karolRotation--
                }
            }
            ArCommandType.ROTATELEFT -> {
                if(karolRotation == 3) {
                    karolRotation = 0
                } else {
                    karolRotation++
                }
            }
            else -> return
        }

        when(karolRotation){
            0 -> {karol.rotation = Rotation(y = 0f);}
            1 -> {karol.rotation = Rotation(y = 90f);}
            2 -> {karol.rotation = Rotation(y = 180f);}
            3 -> {karol.rotation = Rotation(y = 270f);}
            else -> {}
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
                scale = Scale(currentModelScale)
            }
            parent.addChild(karolNode)
            return karolNode
        }
        return null
    }

    private fun createFloor(world: World, parent: ArNode = worldOrigin) {
        var context = this
        for(y in 0 until world.ySize) {
            for(x in 0 until world.xSize) {
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
                    name = "FloorTile"
                    scale = Scale(currentModelScale)
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
                scale = Scale(currentModelScale)
                position = Position(blockSize.x*x, blockSize.y*h, -blockSize.z*y)
                rotation = Rotation(y = 0f)
            }
            parent.addChild(blockNode)
            return blockNode
        }
        return null
    }
}