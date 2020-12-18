package org.itmodreamteam.myrest.server.error.converter

import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.shared.error.Error
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Component

@Component
class AccessDeniedExceptionToErrorsConverter(
    private val userExceptionToErrorsConverter: ConcreteThrowableToErrorsConverter<UserException>
) : ConcreteThrowableToErrorsConverter<AccessDeniedException> {

    override val throwableType: Class<AccessDeniedException> = AccessDeniedException::class.java

    override fun convert(throwable: AccessDeniedException): List<Error> {
        return userExceptionToErrorsConverter.convert(UserException("access-denied"))
    }
}
