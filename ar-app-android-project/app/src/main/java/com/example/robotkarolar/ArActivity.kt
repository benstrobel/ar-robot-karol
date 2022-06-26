package com.example.robotkarolar

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.Anchor
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.*
import com.google.ar.sceneform.rendering.Color as arColor
import android.graphics.Color
import android.widget.*
import androidx.core.graphics.toColorInt
import androidx.core.graphics.toColorLong
import com.google.ar.core.Pose
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Scene
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import kotlin.math.pow
import kotlin.math.sqrt


const val MIN_OPENGL_VERSION = 3.0
const val TAG = "AR"


class ArActivity : AppCompatActivity(), Scene.OnUpdateListener {
    private lateinit var arFragment: ArFragment
    private lateinit var measureButton: ImageButton

    private var measureMode: Boolean = false

    private var renderablePlaced: Boolean = false

    private var distanceCardViewRenderable: ViewRenderable? = null

    private var measurePointsPlaced = 0
    private var placedMarkers: MutableList<AnchorNode> = mutableListOf()
    private val midAnchors: MutableMap<String, Anchor> = mutableMapOf()
    private val midAnchorNodes: MutableMap<String, AnchorNode> = mutableMapOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!checkIsSupportedDeviceOrFinish(this)){
            return
        }

        val bundle = intent.extras
        var furnitureId = -2

        if (bundle != null) {
            furnitureId = bundle.getInt("furnitureId")
        }

        setContentView(R.layout.ar_view)
        arFragment = this.supportFragmentManager.findFragmentById(R.id.ux_fragment) as ArFragment
        arFragment.arSceneView.scene.addOnUpdateListener(this)
        measureButton = this.findViewById(R.id.measure_button) as ImageButton

        ViewRenderable
            .builder()
            .setView(this, R.layout.distance_text)
            .build()
            .thenAccept{
                distanceCardViewRenderable = it
                distanceCardViewRenderable!!.isShadowCaster = false
                distanceCardViewRenderable!!.isShadowReceiver = false
            }
            .exceptionally {
                val builder = AlertDialog.Builder(this)
                builder.setMessage(it.message).setTitle("Error")
                val dialog = builder.create()
                dialog.show()
                return@exceptionally null
            }

        arFragment.setOnTapArPlaneListener { hitResult: HitResult, plane: Plane?, _: MotionEvent? ->
            if (plane?.type == Plane.Type.HORIZONTAL_UPWARD_FACING) {
                if (!measureMode){
                    // only allow placement of one item and not when in MeasureMode
                    if (!renderablePlaced) {
                        renderablePlaced = true
                        placeObject(arFragment, hitResult.createAnchor(), furnitureId)
                    }
                }
                // place measurement markers and if necessary calculate distance
                else {
                    if (measurePointsPlaced == 0) {
                        val anchor = hitResult.createAnchor()
                        measurePointsPlaced = 1
                        placeObject(arFragment, anchor, -1, true)
                    }
                    else if (measurePointsPlaced == 1) {
                        val anchor = hitResult.createAnchor()
                        measurePointsPlaced = 2
                        placeObject(arFragment, anchor, -1, true)

                        val midPosition = floatArrayOf(
                            (placedMarkers[0].worldPosition.x + placedMarkers[1].worldPosition.x) / 2,
                            (placedMarkers[0].worldPosition.y + placedMarkers[1].worldPosition.y) / 2,
                            (placedMarkers[0].worldPosition.z + placedMarkers[1].worldPosition.z) / 2)
                        val quaternion = floatArrayOf(0.0f,0.0f,0.0f,0.0f)
                        val pose = Pose(midPosition, quaternion)

                        placeMidAnchor(pose, distanceCardViewRenderable!!)
                    }

                }
            }
        }
    }

    private fun placeMidAnchor(pose: Pose,
                               renderable: Renderable,
                               between: Array<Int> = arrayOf(0,1)){
        val midKey = "${between[0]}_${between[1]}"
        val anchor = arFragment.arSceneView.session!!.createAnchor(pose)
        midAnchors[midKey] = anchor

        val anchorNode = AnchorNode(anchor).apply {
            isSmoothed = true
            setParent(arFragment.arSceneView.scene)
        }
        midAnchorNodes[midKey] = anchorNode

        TransformableNode(arFragment.transformationSystem)
            .apply{
                this.rotationController.isEnabled = false
                this.scaleController.isEnabled = false
                this.translationController.isEnabled = true
                this.renderable = renderable
                setParent(anchorNode)
            }
        arFragment.arSceneView.scene.addOnUpdateListener(this)
        arFragment.arSceneView.scene.addChild(anchorNode)
    }

    override fun onUpdate(frameTime: FrameTime) {
        measureDistanceOf2Points()
    }

    private fun measureDistanceOf2Points(){
        if (measurePointsPlaced == 2) {
            val distanceMeter = calculateDistance(
                placedMarkers[0].worldPosition,
                placedMarkers[1].worldPosition)
            measureDistanceOf2Points(distanceMeter)
        }
    }

    private fun measureDistanceOf2Points(distanceMeter: Float){
        val distanceTextCM = makeDistanceTextWithCM(distanceMeter)
        val textView = (distanceCardViewRenderable!!.view as LinearLayout)
            .findViewById<TextView>(R.id.distanceCard)
        textView.text = distanceTextCM
        Log.d(TAG, "distance: $distanceTextCM")
    }

    private fun makeDistanceTextWithCM(distanceMeter: Float): String{
        val distanceCMFloor = "%.2f".format(distanceMeter * 100)
        return "$distanceCMFloor cm"
    }

    private fun calculateDistance(x: Float, y: Float, z: Float): Float{
        return sqrt(x.pow(2) + y.pow(2) + z.pow(2))
    }

    private fun calculateDistance(objectPose0: Vector3, objectPose1: Vector3): Float{
        return calculateDistance(
            objectPose0.x - objectPose1.x,
            objectPose0.y - objectPose1.y,
            objectPose0.z - objectPose1.z
        )
    }

    private fun toastMode(){
        if (measureMode) {
            Toast.makeText(
                this,
                "Find plane and tap 2 points",
                Toast.LENGTH_LONG
            ).show()
        }
    }


    fun finishAr(v: View){
        this.finish()
    }

    fun switchMode(v: View) {
        if (measureMode) {
            measureMode = false
            measureButton.setColorFilter(Color.toArgb("#515151".toColorInt().toColorLong()))
        }
        else {
            measureMode = true
            toastMode()
            measureButton.setColorFilter(Color.toArgb("white".toColorInt().toColorLong()))
        }
    }

    fun clearScene(v: View) {
        for(node in arFragment.arSceneView.scene.children) {
            if (node is AnchorNode) {
                if (node.anchor != null) {
                    node.anchor!!.detach()
                }
            }
        }
        renderablePlaced = false

        measurePointsPlaced = 0
        placedMarkers.clear()

        midAnchorNodes.clear()
        midAnchors.clear()

        measureMode = false
        measureButton.setColorFilter(Color.toArgb("#515151".toColorInt().toColorLong()))
    }

    private fun addNodeToScene(arFragment: ArFragment, anchor: Anchor, renderable: Renderable, addAnchorNode: Boolean = false) {
        val anchorNode = AnchorNode(anchor)
        if (addAnchorNode) {
            placedMarkers.add(anchorNode)
        }
        val node = TransformableNode(arFragment.transformationSystem)
        node.scaleController.isEnabled = false
        node.renderable = renderable
        node.setParent(anchorNode)
        arFragment.arSceneView.scene.addOnUpdateListener(this)
        arFragment.arSceneView.scene.addChild(anchorNode)
        node.select()
    }

    fun build(builder: ModelRenderable.Builder, arFragment: ArFragment, anchor: Anchor, addAnchorNode: Boolean = false) {
        builder
            .build()
            .thenAccept { modelRenderable ->
                addNodeToScene(
                    arFragment,
                    anchor,
                    modelRenderable,
                    addAnchorNode
                )
            }
            .exceptionally { throwable: Throwable ->
                val toast = Toast.makeText(
                    arFragment.context,
                    "Error:" + throwable.message,
                    Toast.LENGTH_LONG
                )
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                null
            }
    }


    private fun placeObject(arFragment: ArFragment, anchor: Anchor, furnitureId: Int, addAnchorNode: Boolean = false) {
        var renderable: Renderable?
        val builder = ModelRenderable.builder()

        when (furnitureId) {
            -2 -> {
                builder.setSource(arFragment.context, R.raw.andy)
                build(builder, arFragment, anchor)
            }

            -1 -> {
                MaterialFactory.makeTransparentWithColor(
                    arFragment.context,
                    arColor(Color.RED))
                    .thenAccept { material: Material? ->
                        renderable = ShapeFactory.makeCylinder(
                            0.02f,
                            0.25f,
                            Vector3.zero(),
                            material)
                        renderable!!.isShadowCaster = false
                        renderable!!.isShadowReceiver = false
                        addNodeToScene(arFragment, anchor, renderable as ModelRenderable, addAnchorNode)
                    }
                    .exceptionally {
                            throwable: Throwable ->
                        val toast = Toast.makeText(
                            arFragment.context,
                            "Error:" + throwable.message,
                            Toast.LENGTH_LONG
                        )
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                        null
                    }
            }

            else -> {
                // TODO: actually load furnitures
                builder
                    .setSource(arFragment.context, Uri.parse("ar_models/chair.sfb"))
                build(builder, arFragment, anchor)
            }
        }
    }


    private fun checkIsSupportedDeviceOrFinish(activity: Activity): Boolean {
        val openGlVersionString =
            (activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
                .deviceConfigurationInfo
                .glEsVersion
        if (openGlVersionString.toDouble() < MIN_OPENGL_VERSION) {
            Log.e(
                TAG,
                "Sceneform requires OpenGL ES 3.0 later"
            )
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                .show()
            activity.finish()
            return false
        }
        return true
    }
}