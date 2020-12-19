package org.itmodreamteam.myrest.server.error

import org.itmodreamteam.myrest.shared.error.ServerError

interface ThrowableToErrorsConverter {

    fun convert(throwable: Throwable): List<ServerError>
}
