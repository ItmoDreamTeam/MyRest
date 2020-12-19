package org.itmodreamteam.myrest.server.error.converter

import org.itmodreamteam.myrest.server.error.InterpolatableError
import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.shared.error.ServerError
import org.springframework.stereotype.Component
import javax.validation.ConstraintViolationException

@Component
class ConstraintViolationExceptionToErrorsConverter(
    private val userExceptionToErrorsConverter: ConcreteThrowableToErrorsConverter<UserException>
) : ConcreteThrowableToErrorsConverter<ConstraintViolationException> {

    override val throwableType: Class<ConstraintViolationException> = ConstraintViolationException::class.java

    override fun convert(throwable: ConstraintViolationException): List<ServerError> {
        val errors = throwable.constraintViolations.map { violation ->
            InterpolatableError(violation.message, violation.constraintDescriptor.attributes)
        }
        return userExceptionToErrorsConverter.convert(UserException(errors))
    }
}
