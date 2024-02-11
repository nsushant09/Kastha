package com.neupanesushant.kastha.extra.extensions

fun String.isLongerThan255Char(): Boolean {
    return this.length > 255
}