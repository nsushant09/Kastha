package com.neupanesushant.kastha.libraries.validator

interface Validator {
    fun <T> isValid(data: T): Boolean
}