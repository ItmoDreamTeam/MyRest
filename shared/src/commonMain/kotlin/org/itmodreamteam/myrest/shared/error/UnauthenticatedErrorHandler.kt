package org.itmodreamteam.myrest.shared.error

import kotlin.native.concurrent.ThreadLocal

interface UnauthenticatedErrorHandler {

    fun handle()

    @ThreadLocal
    companion object {
        var INSTANCE: UnauthenticatedErrorHandler? = null
    }
}
