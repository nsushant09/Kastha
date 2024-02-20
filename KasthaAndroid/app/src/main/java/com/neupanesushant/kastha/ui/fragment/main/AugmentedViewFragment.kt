package com.neupanesushant.kastha.ui.fragment.main

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.google.ar.core.Session
import com.google.ar.sceneform.ux.ArFragment
import com.neupanesushant.kastha.domain.model.ObjectModel
import com.neupanesushant.kastha.extra.extensions.show
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

    private var isModelSet = false;
    private lateinit var objectModel: ObjectModel
    private lateinit var session: Session

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

    private fun setupView() {
        planeDiscoveryController.hide()
    }

    private fun setupEventListener() {
        setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            if (isModelSet) return@setOnTapArPlaneListener
            val anchor = hitResult.createAnchor()
            modelManager.buildModel(Uri.parse(objectModel.url)) {
                modelManager.addTransformableNodeModel(this, anchor, it)
            }
            requireContext().show("Displaying Augmented Object")
            isModelSet = true
        }
    }

    private fun setupObserver() {}

    private fun checkArAvailability() {
        if (!arInitializer.isArAvailable())
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
    }
    override fun onDestroy() {
        super.onDestroy()
        if (this::session.isInitialized)
            session.close()
    }
}