package com.neupanesushant.kastha.data.repo

import android.graphics.Bitmap
import android.provider.ContactsContract.CommonDataKinds.Im
import com.neupanesushant.kastha.core.Response
import com.neupanesushant.kastha.domain.model.Image
import com.neupanesushant.kastha.domain.model.dto.BaseResponse

interface ImageRepo {
    suspend fun uploadImages(images: List<Bitmap>): Response<BaseResponse<List<String>>>
}