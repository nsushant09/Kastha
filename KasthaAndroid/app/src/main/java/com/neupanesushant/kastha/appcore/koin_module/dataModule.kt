package com.neupanesushant.kastha.appcore.koin_module

import com.neupanesushant.kastha.data.remote.AuthenticationEndpoint
import com.neupanesushant.kastha.data.remote.Endpoint
import com.neupanesushant.kastha.domain.managers.RetrofitClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {

    single {
        RetrofitClient.instance
            .create(AuthenticationEndpoint::class.java)
    }

    single {
        RetrofitClient.instance
            .create(Endpoint::class.java)
    }

}