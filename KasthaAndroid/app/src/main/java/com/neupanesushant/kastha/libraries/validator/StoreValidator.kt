package com.neupanesushant.kastha.libraries.validator

import com.neupanesushant.kastha.domain.model.Store
import com.neupanesushant.kastha.extra.isLongerThan255Char


class StoreValidator : Validator {
    override fun <T> isValid(data: T): Boolean {
        data as Store

        if (data.name.isEmpty() || data.description.isEmpty() || data.location.isEmpty()) {
            return false
        }

        return (data.name.isLongerThan255Char() || data.location.isLongerThan255Char() || data.description.isLongerThan255Char())
    }
}