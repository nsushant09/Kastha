package com.neupanesushant.kastha.ui.fragment.main

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.google.ar.core.Session
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import com.neupanesushant.kastha.BuildConfig
import com.neupanesushant.kastha.domain.model.ObjectModel
import com.neupanesushant.kastha.extra.extensions.show
import com.neupanesushant.kastha.ui.activity.AugmentedViewActivity
import com.neupanesushant.learnar.ArCore.ArInitializer
import com.neupanesushant.learnar.ArCore.ModelManager

class AugmentedViewFragment : ArFragment() {

    companion object {
        const val MODEL_ARGUMENT = "MODEL_ARGUMENT"
    }

    private val arInitializer by lazy {
        ArInitializer(requireActivity())
    }

    private val modelManager by lazy {
        ModelManager(requireActivity())
    }

    private lateinit var objectModel: ObjectModel
    private lateinit var session: Session

    private var isModelSet = false
        set(value) {
            field = value
            onModelValueChange(value)
        }
    private var transformableNode: TransformableNode? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkArAvailability()
        initialize()
        setupView()
        setupEventListener()
        setupObserver()
    }

    private fun initialize() {
        session = arInitializer.getSession()
        try {
            objectModel = arguments?.getParcelable<ObjectModel>(MODEL_ARGUMENT)!!
        } catch (_: Exception) {
            requireActivity().finish()
        }
    }

    private fun augmentedViewActivity(): AugmentedViewActivity {
        return requireActivity() as AugmentedViewActivity
    }

    private fun setupView() {
        planeDiscoveryController.hide()
    }

    private fun setupEventListener() {
        augmentedViewActivity().binding().btnRemove.setOnClickListener {
            removeCurrentModel()
        }

        setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            if (isModelSet) return@setOnTapArPlaneListener
            val anchor = hitResult.createAnchor()
            modelManager.buildModel(Uri.parse(BuildConfig.BASE_URL + objectModel.url)) {
                transformableNode = modelManager.addTransformableNodeModel(this, anchor, it)
            }
            requireContext().show("Displaying Augmented Object")
            isModelSet = true
        }
    }

    private fun setupObserver() {}

    @SuppressLint("CommitTransaction")
    private fun checkArAvailability() {
        if (!arInitializer.isArAvailable())
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
    }

    private fun removeCurrentModel() {
        transformableNode?.let {
            arSceneView.scene.removeChild(transformableNode)
            transformableNode = null
            isModelSet = false
        }
    }

    private fun onModelValueChange(isModelSet: Boolean) {
        augmentedViewActivity().binding().btnRemove.isVisible = isModelSet
        if (isModelSet) {
        } else {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::session.isInitialized)
            session.close()
    }
}