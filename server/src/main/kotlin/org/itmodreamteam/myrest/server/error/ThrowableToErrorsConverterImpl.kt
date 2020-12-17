package org.itmodreamteam.myrest.server.error

import org.itmodreamteam.myrest.server.error.converter.ConcreteThrowableToErrorsConverter
import org.itmodreamteam.myrest.shared.error.Error
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ThrowableToErrorsConverterImpl(
    private val converters: Set<ConcreteThrowableToErrorsConverter<*>>
) : ThrowableToErrorsConverter {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun convert(throwable: Throwable): List<Error> {
        for (converter in converters) {
            if (converter.throwableType.isAssignableFrom(throwable.javaClass)) {
                return convert(converter, throwable)
            }
        }
        log.error("Unhandled error has occurred: ${throwable.message}", throwable)
        return listOf(Error.unknown(throwable.message ?: ""))
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Throwable> convert(
        converter: ConcreteThrowableToErrorsConverter<T>,
        throwable: Throwable
    ): List<Error> {
        return converter.convert(throwable as T)
    }
}
