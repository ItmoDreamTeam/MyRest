package org.itmodreamteam.myrest.shared.error

import kotlin.native.concurrent.ThreadLocal

interface DefaultErrorHandler {

    fun handle(clientException: ClientException)

    @ThreadLocal
    companion object {
        var INSTANCE: DefaultErrorHandler? = null
    }
}
