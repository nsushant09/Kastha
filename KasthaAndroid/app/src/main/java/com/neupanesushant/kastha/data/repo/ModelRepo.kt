package com.neupanesushant.kastha.data.repo

import com.neupanesushant.kastha.core.Response
import com.neupanesushant.kastha.domain.model.dto.BaseResponse
import okhttp3.MultipartBody
import java.io.File

interface ModelRepo {
    suspend fun uploadModel(file: MultipartBody.Part): Response<BaseResponse<String>>
}