package com.neupanesushant.kastha.domain.repo_impl.remote

import com.neupanesushant.kastha.data.remote.Endpoint
import com.neupanesushant.kastha.data.repo.AlignmentRepo
import com.neupanesushant.kastha.domain.model.Alignment

class AlignmentRemoteImpl(
    private val endpoint: Endpoint
) : AlignmentRepo {
    override suspend fun getAlignments()= endpoint.getAlignments()

    override suspend fun getAlignmentOf(id: Int): Alignment = endpoint.getAlignmentOf(id)

}