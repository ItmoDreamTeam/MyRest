package org.itmodreamteam.myrest.server.error

import org.assertj.core.api.Assertions.assertThat
import org.itmodreamteam.myrest.shared.error.Error
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@ContextConfiguration(classes = [ErrorConverterTestConfig::class])
class ThrowableToErrorsConverterTest {

    @Autowired
    lateinit var converter: ThrowableToErrorsConverter

    @Test
    fun `When convert unhandled exception, then error unknown with developer message`() {
        val message = "Неизвестная ошибка"
        val errors = converter.convert(RuntimeException(message))
        assertThat(errors).hasSize(1)
        val error = errors.first()
        assertThat(error.key).isEqualTo(Error.UNKNOWN_ERROR_KEY)
        assertThat(error.userMessage).isEqualTo(Error.UNKNOWN_ERROR_USER_MESSAGE)
        assertThat(error.developerMessage).isEqualTo(message)
    }

    @Test
    fun `When convert empty user exception, then error unknown`() {
        val errors = converter.convert(UserException())
        assertThat(errors).hasSize(1)
        val error = errors.first()
        assertThat(error.key).isEqualTo(Error.UNKNOWN_ERROR_KEY)
        assertThat(error.userMessage).isEqualTo(Error.UNKNOWN_ERROR_USER_MESSAGE)
        assertThat(error.developerMessage).isNull()
    }

    @Test
    fun `When convert user exception and user message not specified, then user message is default`() {
        val key = "key.without.user-message"
        val errors = converter.convert(UserException(key))
        assertThat(errors).hasSize(1)
        val error = errors.first()
        assertThat(error.key).isEqualTo(key)
        assertThat(error.userMessage).isEqualTo(Error.UNKNOWN_ERROR_USER_MESSAGE)
        assertThat(error.developerMessage).isNull()
    }

    @Test
    fun `When convert simple user exception, then key and messages are correct`() {
        val key = "user.not-found"
        val message = "Пользователь не найден"
        val errors = converter.convert(UserException(key))
        assertThat(errors).hasSize(1)
        val error = errors.first()
        assertThat(error.key).isEqualTo(key)
        assertThat(error.userMessage).isEqualTo(message)
        assertThat(error.developerMessage).isNull()
    }

    @Test
    fun `When convert interpolatable user exception with single parameter, then key and messages are correct`() {
        val key = "user.exists"
        val phone = "+79218881243"
        val message = "Пользователь с номером телефона +79218881243 уже зарегистрирован"
        val errors = converter.convert(UserException(key, "phone" to phone))
        assertThat(errors).hasSize(1)
        val error = errors.first()
        assertThat(error.key).isEqualTo(key)
        assertThat(error.userMessage).isEqualTo(message)
        assertThat(error.developerMessage).isNull()
    }

    @Test
    fun `When convert interpolatable user exception with multiple parameters, then key and messages are correct`() {
        val key = "user.username.size"
        val minSize = 3
        val maxSize = 16
        val message = "Длина имени пользователя должна быть от 3 до 16 символов"
        val errors = converter.convert(UserException(key, mapOf("min" to minSize, "max" to maxSize)))
        assertThat(errors).hasSize(1)
        val error = errors.first()
        assertThat(error.key).isEqualTo(key)
        assertThat(error.userMessage).isEqualTo(message)
        assertThat(error.developerMessage).isNull()
    }
}
