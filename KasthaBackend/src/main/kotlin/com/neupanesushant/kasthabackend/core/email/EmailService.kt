package com.neupanesushant.kasthabackend.core.email

interface EmailService {
    fun send(to: String): HashMap<String, String>
    fun emailContent(): String
}