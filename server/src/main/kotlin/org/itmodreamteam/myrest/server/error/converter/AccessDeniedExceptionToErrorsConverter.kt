package org.itmodreamteam.myrest.server.error.converter

import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.shared.error.ServerError
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Component

@Component
class AccessDeniedExceptionToErrorsConverter(
    userExceptionToErrorsConverter: ConcreteThrowableToErrorsConverter<UserException>
) : ConcreteThrowableToErrorsConverter<AccessDeniedException> {

    private val errors = userExceptionToErrorsConverter.convert(UserException("access-denied"))

    override val throwableType: Class<AccessDeniedException> = AccessDeniedException::class.java

    override fun convert(throwable: AccessDeniedException): List<ServerError> = errors
}
