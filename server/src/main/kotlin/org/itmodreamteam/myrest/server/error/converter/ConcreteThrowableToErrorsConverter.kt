package org.itmodreamteam.myrest.server.error.converter

import org.itmodreamteam.myrest.shared.error.Error

interface ConcreteThrowableToErrorsConverter<T : Throwable> {

    val throwableType: Class<T>

    fun convert(throwable: T): List<Error>
}
