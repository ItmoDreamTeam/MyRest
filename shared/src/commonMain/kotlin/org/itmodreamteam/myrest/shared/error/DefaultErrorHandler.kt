package org.itmodreamteam.myrest.shared.error

interface DefaultErrorHandler {

    fun handle(clientException: ClientException)

    companion object {
        var INSTANCE: DefaultErrorHandler? = null
    }
}
