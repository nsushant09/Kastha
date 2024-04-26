package com.neupanesushant.kastha.domain.repo_impl.remote

import android.content.Context
import com.neupanesushant.kastha.core.Response
import com.neupanesushant.kastha.core.requestHandler
import com.neupanesushant.kastha.data.remote.Endpoint
import com.neupanesushant.kastha.data.repo.ImageRepo
import com.neupanesushant.kastha.domain.model.dto.BaseResponse
import okhttp3.MultipartBody
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ImageRemoteImpl(
    private val endpoint: Endpoint
) : ImageRepo{
    override suspend fun uploadImages(images: List<MultipartBody.Part>): Response<BaseResponse<List<String>>> =
        requestHandler {
            endpoint.uploadImages(images)
        }
}