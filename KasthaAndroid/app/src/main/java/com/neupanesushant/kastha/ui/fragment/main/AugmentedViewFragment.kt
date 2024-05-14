package com.neupanesushant.kastha.ui.fragment.main

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.ux.ArFragment
import com.neupanesushant.kastha.BuildConfig
import com.neupanesushant.kastha.appcore.ArCore.ArInitializer
import com.neupanesushant.kastha.domain.model.Alignment
import com.neupanesushant.kastha.domain.model.ObjectModel
import com.neupanesushant.kastha.extra.extensions.show
import com.neupanesushant.kastha.ui.activity.AugmentedViewActivity
import com.neupanesushant.learnar.ArCore.ModelManager

class AugmentedViewFragment : ArFragment() {

    companion object {
        const val MODEL_ARGUMENT = "MODEL_ARGUMENT"
        const val ALIGNMENT_ARGUMENT = "ALIGNMENT_ARGUMENT"
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
    private var node: Node? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        setupView()
        setupEventListener()
        setupObserver()
    }

    private fun initialize() {
        session = arInitializer.getSession()
        try {
            objectModel = arguments?.getParcelable<ObjectModel>(MODEL_ARGUMENT)!!
            val alignment = arguments?.getParcelable<Alignment>(ALIGNMENT_ARGUMENT)!!
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
            modelManager.buildModel(
                Uri.parse(BuildConfig.BASE_URL + objectModel.url),
                onModelBuilt = {
                    node = modelManager.addTransformableNodeModel(this, anchor, it)

                }, onModelFailure = {
                    requireContext().show(it ?: "Could not show object")
                    isModelSet = false
                })
            requireContext().show("Displaying Augmented Object")
            isModelSet = true
        }
    }

    private fun setupObserver() {
    }

    private fun removeCurrentModel() {
        node?.let {
            arSceneView.scene.removeChild(it)
            node = null
            isModelSet = false
        }
    }

    private fun onModelValueChange(isModelSet: Boolean) {
        augmentedViewActivity().binding().btnRemove.isVisible = isModelSet
    }

    override fun onDestroy() {
        super.onDestroy()
        if (this::session.isInitialized)
            session.close()
    }
}