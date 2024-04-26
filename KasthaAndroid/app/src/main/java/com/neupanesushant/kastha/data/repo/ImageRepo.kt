package com.neupanesushant.kastha.data.repo

import com.neupanesushant.kastha.core.Response
import com.neupanesushant.kastha.domain.model.dto.BaseResponse
import okhttp3.MultipartBody

interface ImageRepo {
    suspend fun uploadImages(images: List<MultipartBody.Part>): Response<BaseResponse<List<String>>>
}