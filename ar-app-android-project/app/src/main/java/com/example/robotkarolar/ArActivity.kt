package com.example.robotkarolar

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.utils.setFullScreen

class ArActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var sceneView: ArSceneView
    private lateinit var placeButton: ExtendedFloatingActionButton
    private val modelScale = 0.15f
    private var karolCreated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFullScreen(
            findViewById(R.id.rootView),
            fullScreen = true,
            hideSystemBars = false,
            fitsSystemWindows = false
        )

        sceneView = findViewById(R.id.sceneView)
        sceneView.planeRenderer.isVisible = false
        placeButton = findViewById<ExtendedFloatingActionButton>(R.id.placeModelButton).apply {
            setOnClickListener{ debug() }
        }

        sceneView.onArFrame = {
            if(!karolCreated && it.isTrackingPlane) {
                createKarol()
                karolCreated = true
            }
        }

        //TODO: Testing remove
        //createGrasBlock(Position(y = -0.0f, x = -2.0f, z = -0.0f))
        //createStoneBlock(Position(y = -1.0f, x = -1.0f, z = -1.0f)) //issues with glb file
        //createWaterBlock(Position(y = -0.0f, x = -4.0f, z = -0.0f))
    }

    private fun debug() {
        createGrasBlock(Position(1.0f, 0.0f, 0.0f))
    }

    private fun createKarol(){
        var context = this;
        var karolNode = ArModelNode(PlacementMode.PLANE_HORIZONTAL).apply {
            loadModelAsync(
                context = context,
                lifecycle = lifecycle,
                glbFileLocation = "model_steve/steveSmall.glb",
                autoScale = true
            )
            name = "Karol"
            instantAnchor = true
            position = Position(0.0f, 0.0f, 0.0f)
            scale = scale.times(this@ArActivity.modelScale)
        }
        sceneView.apply {
            addChild(karolNode)
        }
    }

    fun rotateKarol() {
        sceneView.children.first { it.name == "Karol" }.rotation = Rotation(y = 180.0f) //TODO: Change rotation due to North, East, West, South
    }

    fun createBlock(pos: Position, fileLocation: String) {
        var context = this;
        var block = ArModelNode(PlacementMode.PLANE_HORIZONTAL).apply {
            loadModelAsync(
                context = context,
                lifecycle = lifecycle,
                glbFileLocation = fileLocation,
                autoScale = true,
            )
            name = "Karol"
            instantAnchor = true
            position = pos
            scale = scale.times(this@ArActivity.modelScale /2)
        }
        sceneView.apply {
            addChild(block)
        }
    }

    fun createGrasBlock(pos: Position){
        createBlock(pos, "model_grasblock/gras.glb")
    }

    fun createStoneBlock(pos: Position){
        createBlock(pos, "model_stoneblock/stone.glb")
    }

    fun createWaterBlock(pos: Position){
        createBlock(pos, "model_waterblock/water.glb")
    }

    fun finishAr(v: View){
        this.finish()
    }
}