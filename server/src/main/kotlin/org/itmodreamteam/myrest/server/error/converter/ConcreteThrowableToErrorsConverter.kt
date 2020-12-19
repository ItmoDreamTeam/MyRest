package org.itmodreamteam.myrest.server.error.converter

import org.itmodreamteam.myrest.shared.error.ServerError

interface ConcreteThrowableToErrorsConverter<T : Throwable> {

    val throwableType: Class<T>

    fun convert(throwable: T): List<ServerError>
}
