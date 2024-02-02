package com.neupanesushant.kastha.libraries.validator

import com.neupanesushant.kastha.domain.model.User

class UserValidator : Validator {
    override fun <T> isValid(data: T): Boolean {
        data as User
        if (data.email.matches(Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$"))){

        }
            return true
    }
}