package com.neupanesushant.kastha.domain.usecase

import com.neupanesushant.kastha.data.repo.AlignmentRepo
import com.neupanesushant.kastha.domain.model.Alignment

class AlignmentUseCase(
    private val alignmentRepo: AlignmentRepo
) {

    private val alignments: List<Alignment>? = null
    suspend fun getAlignments(): List<Alignment> {
        return alignments ?: alignmentRepo.getAlignments()
    }

    suspend fun getAlignmentOfId(id: Int): Alignment {
        val alignment = alignments?.find { it.id == id }
        return alignment ?: alignmentRepo.getAlignmentOf(id)
    }
}