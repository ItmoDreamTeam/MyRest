package org.itmodreamteam.myrest.server.error.converter

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.shared.error.Error
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class UserExceptionToErrorsConverter : ConcreteThrowableToErrorsConverter<UserException> {

    private val log = LoggerFactory.getLogger(javaClass)
    private val errors: Map<String, String> = ErrorsReader().readErrorsMessages()

    override val throwableType: Class<UserException> = UserException::class.java

    override fun convert(throwable: UserException): List<Error> {
        return throwable.errorsKeys.map { key ->
            val message = errors[key]
            if (message == null) {
                log.warn("No message found for error key '$key'")
                return@map Error(key)
            }
            Error(key, message)
        }.ifEmpty { listOf(Error.unknown()) }
    }

    private class ErrorsReader {
        private val errorsResourcePath = "/errors.yml"
        private val keyDelimiter = "."

        private lateinit var errors: MutableMap<String, String>

        fun readErrorsMessages(): Map<String, String> {
            val errorsResourceStream = javaClass.getResourceAsStream(errorsResourcePath)
            val rootNode = ObjectMapper(YAMLFactory()).readTree(errorsResourceStream)
            errors = HashMap()
            readErrorsMessages(rootNode, "")
            return errors
        }

        private fun readErrorsMessages(node: JsonNode, keyPrefix: String) {
            node.fields().forEach { field ->
                val key = keyPrefix + field.key
                val value = field.value
                if (value.isTextual) {
                    errors[key] = value.textValue()
                } else {
                    readErrorsMessages(value, key + keyDelimiter)
                }
            }
        }
    }
}
