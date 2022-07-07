package com.example.robotkarolar

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.EditableTransform
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position
import io.github.sceneview.utils.setFullScreen

class ArActivity : AppCompatActivity(R.layout.activity_main) {

    lateinit var sceneView: ArSceneView
    lateinit var loadingView: View

    lateinit var modelNode: ArModelNode

    var isLoading = false
        set(value) {
            field = value
            loadingView.isGone = !value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFullScreen(
            findViewById(R.id.rootView),
            fullScreen = true,
            hideSystemBars = false,
            fitsSystemWindows = false
        )

        sceneView = findViewById(R.id.sceneView)
        loadingView = findViewById(R.id.loadingView)

        isLoading = true
        modelNode = ArModelNode(
            context = this,
            lifecycle = lifecycle,
            //modelFileLocation = "model/scene.gltf",
            //modelFileLocation = "minecraft_block/scene.gltf",
            //modelFileLocation = "model_grasblock/gras.glb",
            modelFileLocation = "model_waterblock/water.glb",
            autoAnimate = true,
            autoScale = false,
            // Place the model origin at the bottom center
            centerOrigin = Position(x= 0.0f, y = 0.0f, z = 0.0f)
        ) {
            isLoading = false
        }.apply {
            instantAnchor = true
            sceneView.planeRenderer.isVisible = false
            placementMode = PlacementMode.BEST_AVAILABLE
            /*onPoseChanged = { node, _ ->
                actionButton.isGone = !node.isTracking
            }*/
            editableTransforms = EditableTransform.ALL
        }
        sceneView.apply {
            addChild(modelNode)
            // Select the model node by default (the model node is also selected on tap)
            gestureDetector.selectedNode = modelNode

            modelNode.anchor()
        }

        //TODO: Testing remove
        createGrasBlock(Position(y = -0.0f, x = -2.0f, z = -0.0f))
        //createStoneBlock(Position(y = -1.0f, x = -1.0f, z = -1.0f))
        createWaterBlock(Position(y = -0.0f, x = -4.0f, z = -0.0f))
    }

    fun createGrasBlock(position: Position){
        var grasBlock = ArModelNode(
            context = this,
            lifecycle = lifecycle,
            modelFileLocation = "model_grasblock/gras.glb",
            autoAnimate = true,
            autoScale = false,
            // Place the model origin at the bottom center
            centerOrigin = position
        ).apply {
            instantAnchor = true //anchored from beginning
            sceneView.planeRenderer.isVisible = false //removes Plant detection
        }
        sceneView.apply {
            addChild(grasBlock)
            modelNode.anchor() //anchors the block
        }
    }

    fun createStoneBlock(position: Position){
        var stoneBlock = ArModelNode(
            context = this,
            lifecycle = lifecycle,
            modelFileLocation = "model_stoneblock/stone.glb",
            autoAnimate = true,
            autoScale = false,
            // Place the model origin at the bottom center
            centerOrigin = position
        ).apply {
            instantAnchor = true //anchored from beginning
            sceneView.planeRenderer.isVisible = false //removes Plant detection
        }
        sceneView.apply {
            addChild(stoneBlock)
            modelNode.anchor() //anchors the block
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
            sceneView.planeRenderer.isVisible = false //removes Plant detection
        }
        sceneView.apply {
            addChild(waterBlock)
            modelNode.anchor() //anchors the block
        }
    }

    fun finishAr(v: View){
        this.finish()
    }

    /*fun actionButtonClicked() {
        if (!modelNode.isAnchored && modelNode.anchor()) {
            //actionButton.text = getString(R.string.move_object)
            //actionButton.setIconResource(R.drawable.ic_target)
            sceneView.planeRenderer.isVisible = false
        } else {
            modelNode.anchor = null
            //actionButton.text = getString(R.string.place_object)
            //actionButton.setIconResource(R.drawable.ic_anchor)
            sceneView.planeRenderer.isVisible = true
        }
    }*/
}