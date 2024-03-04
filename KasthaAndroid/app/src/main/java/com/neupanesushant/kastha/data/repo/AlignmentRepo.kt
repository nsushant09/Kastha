package com.neupanesushant.kastha.data.repo

import com.neupanesushant.kastha.domain.model.Alignment

interface AlignmentRepo {
    suspend fun getAlignments(): List<Alignment>
    suspend fun getAlignmentOf(id: Int): Alignment

}