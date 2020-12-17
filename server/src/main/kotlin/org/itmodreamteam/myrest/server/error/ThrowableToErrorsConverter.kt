package org.itmodreamteam.myrest.server.error

import org.itmodreamteam.myrest.shared.error.Error

interface ThrowableToErrorsConverter {

    fun convert(throwable: Throwable): List<Error>
}
