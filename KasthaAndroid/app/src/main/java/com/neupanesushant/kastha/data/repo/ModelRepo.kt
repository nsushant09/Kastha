package com.neupanesushant.kastha.data.repo

import com.neupanesushant.kastha.core.Response
import com.neupanesushant.kastha.domain.model.dto.BaseResponse
import java.io.File

interface ModelRepo {
    suspend fun uploadModel(file: File): Response<BaseResponse<String>>
}