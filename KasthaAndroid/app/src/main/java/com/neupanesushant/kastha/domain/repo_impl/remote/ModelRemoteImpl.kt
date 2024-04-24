package com.neupanesushant.kastha.domain.repo_impl.remote

import com.neupanesushant.kastha.core.Response
import com.neupanesushant.kastha.core.requestHandler
import com.neupanesushant.kastha.data.remote.Endpoint
import com.neupanesushant.kastha.data.repo.ModelRepo
import com.neupanesushant.kastha.domain.model.dto.BaseResponse
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ModelRemoteImpl(
    private val endpoint: Endpoint
) : ModelRepo {
    override suspend fun uploadModel(file: File): Response<BaseResponse<String>> =
        requestHandler {
            endpoint.uploadModel(
                MultipartBody.Part.create(
                    RequestBody.create(
                        MediaType.parse(
                            "model/gltf-binary",
                        ),
                        file
                    )
                )
            )
        }
}