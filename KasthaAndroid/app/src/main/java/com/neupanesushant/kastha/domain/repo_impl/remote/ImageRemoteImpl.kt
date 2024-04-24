package com.neupanesushant.kastha.domain.repo_impl.remote

import com.neupanesushant.kastha.core.Response
import com.neupanesushant.kastha.core.requestHandler
import com.neupanesushant.kastha.data.remote.Endpoint
import com.neupanesushant.kastha.data.repo.ImageRepo
import com.neupanesushant.kastha.domain.model.dto.BaseResponse

class ImageRemoteImpl(
    private val endpoint: Endpoint
) : ImageRepo {
    override suspend fun uploadImages(): Response<BaseResponse<List<String>>> {

        return requestHandler { endpoint.uploadImages(emptyList()) }
    }
}