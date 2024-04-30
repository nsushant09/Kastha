package com.neupanesushant.kastha.domain.repo_impl.remote

import com.neupanesushant.kastha.core.Response
import com.neupanesushant.kastha.core.requestHandler
import com.neupanesushant.kastha.data.remote.Endpoint
import com.neupanesushant.kastha.data.repo.ModelRepo
import com.neupanesushant.kastha.domain.model.ObjectModel
import com.neupanesushant.kastha.domain.model.dto.BaseResponse
import okhttp3.MultipartBody

class ModelRemoteImpl(
    private val endpoint: Endpoint
) : ModelRepo {
    override suspend fun uploadModel(file: MultipartBody.Part): Response<BaseResponse<String>> =
        requestHandler {
            endpoint.uploadModel(file)
        }
}