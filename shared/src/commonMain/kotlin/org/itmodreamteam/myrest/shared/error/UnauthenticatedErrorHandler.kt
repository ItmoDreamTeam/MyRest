package org.itmodreamteam.myrest.shared.error

interface UnauthenticatedErrorHandler {

    fun handle()

    companion object {
        var INSTANCE: UnauthenticatedErrorHandler? = null
    }
}
