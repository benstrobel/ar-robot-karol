package com.example.robotkarolar

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isGone
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.EditableTransform
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position
import io.github.sceneview.utils.doOnApplyWindowInsets
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
            //modelFileLocation = "models/spiderbot.glb",
            //modelFileLocation = "model/scene.gltf",
            modelFileLocation = "minecraft_block/scene.gltf",
            autoAnimate = true,
            autoScale = true,
            // Place the model origin at the bottom center
            centerOrigin = Position(y = -1.0f)
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