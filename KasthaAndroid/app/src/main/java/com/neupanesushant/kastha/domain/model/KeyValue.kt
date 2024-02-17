package com.neupanesushant.kastha.domain.model

data class KeyValue<K, V>(
    val key: K,
    val value: V
)

data class LoginDTO(
    val email: String,
    val password: String
)