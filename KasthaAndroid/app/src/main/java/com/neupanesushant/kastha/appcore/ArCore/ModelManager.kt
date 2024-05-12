package com.neupanesushant.learnar.ArCore

import android.content.Context
import android.net.Uri
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import com.neupanesushant.learnar.ArCore.UriRetriever.getRawUri

class ModelManager(private val context: Context) {
    fun buildModel(
        uri: Uri,
        onModelBuilt: (ModelRenderable) -> Unit,
        onModelFailure: (String?) -> Unit = {}
    ) {
        ModelRenderable.builder().setSource(
            context,
            RenderableSource.builder().setSource(context, uri, RenderableSource.SourceType.GLB)
                .build()
        ).setRegistryId(uri.toString()).build()
            .thenAccept {
                onModelBuilt(it)
            }.exceptionally {
                onModelFailure(it.message)
                null
            }
    }

    fun buildModel(
        url: String,
        onModelBuilt: (ModelRenderable) -> Unit,
        onModelFailure: (String?) -> Unit = {}
    ) {
        val uri = context.getRawUri(url)
        buildModel(uri, onModelBuilt, onModelFailure)
    }

    fun addModel(fragment: ArFragment, anchor: Anchor, renderable: ModelRenderable) {
        val node = AnchorNode(anchor)
        node.renderable = renderable
        fragment.arSceneView.scene.addChild(node)
    }

    fun addTransformableNodeModel(
        fragment: ArFragment, anchor: Anchor, renderable: ModelRenderable
    ): Node {
        val node = AnchorNode(anchor)
        val transformableNode = TransformableNode(fragment.transformationSystem)
        transformableNode.setParent(node)
        transformableNode.renderable = renderable
        fragment.arSceneView.scene.addChild(node)
        transformableNode.select()
        return node
    }
}