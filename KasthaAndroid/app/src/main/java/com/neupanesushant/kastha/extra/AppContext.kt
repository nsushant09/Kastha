package com.neupanesushant.kastha.extra

object AppContext {
    // Changed from Network change receiver
    var isOnline = false
    val isOffline get() = !isOnline
}