package com.neupanesushant.kastha.domain.databasepersistence

import android.net.Uri

interface DatabasePersistence {
    suspend operator fun invoke(fromId : Int, uri : Uri) : String
}