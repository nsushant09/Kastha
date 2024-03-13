package com.neupanesushant.kastha.data.repo

import com.neupanesushant.kastha.core.Response
import com.neupanesushant.kastha.domain.model.Alignment


interface AlignmentRepo {
    suspend fun getAlignments(): Response<List<Alignment>>
    suspend fun getAlignmentOf(id: Int): Response<Alignment>

}