package com.neupanesushant.kastha.extra.helper

object Validator {
    fun email(value: String): Pair<Boolean, String> {
        val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,})+\$")
        if (value.isBlank()) {
            return Pair(false, "Email cannot be empty.")
        }
        if (!value.matches(emailRegex)) {
            return Pair(false, "Invalid email format. Please enter a valid email address.")
        }
        if (value.length > 254) {
            return Pair(false, "Email is too long. Maximum length is 254 characters.")
        }
        if (value.contains(Regex("[^A-Za-z0-9.@_-]"))) {
            return Pair(
                false,
                "Email contains invalid characters. Only alphanumeric characters, dots, underscores, hyphens, and '@' are allowed."
            )
        }
        return Pair(true, "Valid email")
    }

    fun password(value: String): Pair<Boolean, String> {
        if (value.isBlank()) {
            return Pair(false, "Password cannot be empty.")
        }
        if (value.length < 8 || value.length > 32) {
            return Pair(false, "Password length must be between 8 and 32 characters.")
        }
        val lowercaseRegex = Regex("[a-z]")
        val uppercaseRegex = Regex("[A-Z]")
        val digitRegex = Regex("[0-9]")

        if (!value.contains(lowercaseRegex) || !value.contains(uppercaseRegex) || !value.contains(
                digitRegex
            )
        ) {
            return Pair(
                false,
                "Password must contain at least one lowercase letter, one uppercase letter, and one digit."
            )
        }
        return Pair(true, "Valid password")
    }

    fun generalString(value: String): Pair<Boolean, String> {
        if (value.isBlank()) {
            return Pair(false, "Input cannot be empty.")
        }
        val numericRegex = Regex("[0-9]")
        return if (!value.contains(numericRegex)) {
            Pair(true, "Valid string")
        } else {
            Pair(false, "Input should not contain numeric characters.")
        }
    }
}