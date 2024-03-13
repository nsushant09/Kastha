package com.neupanesushant.kastha.domain.repo_impl.remote

import com.neupanesushant.kastha.core.requestHandler
import com.neupanesushant.kastha.data.remote.Endpoint
import com.neupanesushant.kastha.data.repo.AlignmentRepo

class AlignmentRemoteImpl(
    private val endpoint: Endpoint
) : AlignmentRepo {
    override suspend fun getAlignments() = requestHandler { endpoint.alignments }

    override suspend fun getAlignmentOf(id: Int) = requestHandler { endpoint.getAlignmentOf(id) }

}