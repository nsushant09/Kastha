package com.neupanesushant.kastha.data.repo

import com.neupanesushant.kastha.core.Response
import com.neupanesushant.kastha.domain.model.dto.BaseResponse

interface ImageRepo {
    suspend fun uploadImages(): Response<BaseResponse<List<String>>>
}