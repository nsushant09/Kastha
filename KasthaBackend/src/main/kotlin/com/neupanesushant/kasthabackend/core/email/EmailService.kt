package com.neupanesushant.kasthabackend.core.email

interface EmailService {
    fun send(to: String): HashMap<String, Any>
    fun emailContent(): String
}