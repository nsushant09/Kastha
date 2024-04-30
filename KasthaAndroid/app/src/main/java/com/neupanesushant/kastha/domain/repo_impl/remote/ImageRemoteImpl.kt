package com.neupanesushant.kastha.domain.repo_impl.remote

import android.graphics.Bitmap
import com.neupanesushant.kastha.core.Response
import com.neupanesushant.kastha.core.requestHandler
import com.neupanesushant.kastha.data.remote.Endpoint
import com.neupanesushant.kastha.data.repo.ImageRepo
import com.neupanesushant.kastha.domain.model.Image
import com.neupanesushant.kastha.domain.model.dto.BaseResponse
import com.neupanesushant.kastha.extra.Utils.toMultipart

class ImageRemoteImpl(
    private val endpoint: Endpoint
) : ImageRepo {
    override suspend fun uploadImages(images: List<Bitmap>): Response<BaseResponse<List<String>>> =
        requestHandler {
            endpoint.uploadImages(images.map { it.toMultipart("files") })
        }
}