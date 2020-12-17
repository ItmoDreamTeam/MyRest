package org.itmodreamteam.myrest.server.error

import org.itmodreamteam.myrest.shared.error.Error
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RestApiExceptionHandler(private val converter: ThrowableToErrorsConverter) {

    @ExceptionHandler(Throwable::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handle(throwable: Throwable): List<Error> = converter.convert(throwable)
}
