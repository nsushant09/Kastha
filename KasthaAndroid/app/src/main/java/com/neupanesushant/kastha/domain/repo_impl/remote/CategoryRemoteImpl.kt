package com.neupanesushant.kastha.domain.repo_impl.remote

import com.neupanesushant.kastha.core.requestHandler
import com.neupanesushant.kastha.data.remote.Endpoint
import com.neupanesushant.kastha.data.repo.CategoryRepo
import com.neupanesushant.kastha.domain.model.Category

class CategoryRemoteImpl(
    private val endpoint: Endpoint
) : CategoryRepo {
    override suspend fun getCategories() =
        requestHandler { endpoint.getCategories() }

    override suspend fun getCategoryOf(id: Int): Category = endpoint.getCategoryOf(id)
}