package com.example.robotkarolar

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import dev.romainguy.kotlin.math.scale
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.EditableTransform
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.math.Scale
import io.github.sceneview.utils.setFullScreen

class ArActivity : AppCompatActivity(R.layout.activity_main) {

    lateinit var sceneView: ArSceneView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFullScreen(
            findViewById(R.id.rootView),
            fullScreen = true,
            hideSystemBars = false,
            fitsSystemWindows = false
        )

        sceneView = findViewById(R.id.sceneView)

        createKarol(Position(x= 0.0f, y = 0.0f, z = 0.0f))
        rotateKarol()

        //TODO: Testing remove
        //createGrasBlock(Position(y = -0.0f, x = -2.0f, z = -0.0f))
        //createStoneBlock(Position(y = -1.0f, x = -1.0f, z = -1.0f)) //issues with glb file
        //createWaterBlock(Position(y = -0.0f, x = -4.0f, z = -0.0f))
    }

    fun createKarol(position: Position){
        var karolNode = ArModelNode(
            context = this,
            lifecycle = lifecycle,
            modelFileLocation = "model_steve/steveSmall.glb",
            autoAnimate = true,
            autoScale = false,
            centerOrigin = position
        ).apply {
            name = "Karol"
            scale = Scale(0.2f)
            instantAnchor = true //anchored from beginning
            sceneView.planeRenderer.isVisible = false //removes Plant detection
        }
        sceneView.apply {
            addChild(karolNode)
            karolNode.anchor() //anchors the block
        }
    }

    fun rotateKarol() {
        sceneView.children.first { it.name == "Karol" }.rotation = Rotation(y = 180.0f) //TODO: Change rotation due to North, East, West, South
    }

    fun createGrasBlock(position: Position){
        var grasBlock = ArModelNode(
            context = this,
            lifecycle = lifecycle,
            modelFileLocation = "model_grasblock/gras.glb",
            autoAnimate = true,
            autoScale = false,
            centerOrigin = position
        ).apply {
            instantAnchor = true //anchored from beginning
            //sceneView.planeRenderer.isVisible = false //removes Plant detection
        }
        sceneView.apply {
            addChild(grasBlock)
            grasBlock.anchor() //anchors the block
        }
    }

    fun createStoneBlock(position: Position){
        var stoneBlock = ArModelNode(
            context = this,
            lifecycle = lifecycle,
            modelFileLocation = "model_stoneblock/stone.glb", //something wrong with the glb file
            autoAnimate = true,
            autoScale = false,
            centerOrigin = position
        ).apply {
            instantAnchor = true //anchored from beginning
            //sceneView.planeRenderer.isVisible = false //removes Plant detection
        }
        sceneView.apply {
            addChild(stoneBlock)
            stoneBlock.anchor() //anchors the block
        }
    }

    fun createWaterBlock(position: Position){
        var waterBlock = ArModelNode(
            context = this,
            lifecycle = lifecycle,
            modelFileLocation = "model_waterblock/water.glb",
            autoAnimate = true,
            autoScale = false,
            // Place the model origin at the bottom center
            centerOrigin = position
        ).apply {
            instantAnchor = true //anchored from beginning
            //sceneView.planeRenderer.isVisible = false //removes Plant detection
        }
        sceneView.apply {
            addChild(waterBlock)
            waterBlock.anchor() //anchors the block
        }
    }

    fun finishAr(v: View){
        this.finish()
    }
}