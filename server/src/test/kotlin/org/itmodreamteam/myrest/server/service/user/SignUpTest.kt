package org.itmodreamteam.myrest.server.service.user

import org.assertj.core.api.Assertions.assertThat
import org.itmodreamteam.myrest.server.model.user.Identifier
import org.itmodreamteam.myrest.server.repository.user.IdentifierRepository
import org.itmodreamteam.myrest.server.repository.user.UserRepository
import org.itmodreamteam.myrest.server.service.sms.SmsService
import org.itmodreamteam.myrest.shared.user.SignUp
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataJpaTest
@ContextConfiguration(classes = [SignUpTest.Config::class])
class SignUpTest {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var identifierRepository: IdentifierRepository

    @MockBean
    lateinit var smsService: SmsService

    @Test
    fun `When sign up, then new user is created`() {
        val signUp = SignUp("Joshua", "Ivanov", "+79210017007")
        userService.signUp(signUp)

        val users = userRepository.findAll()
        assertThat(users).hasSize(1)

        val user = users[0]
        assertThat(user.firstName).isEqualTo("Joshua")
        assertThat(user.lastName).isEqualTo("Ivanov")
        assertThat(user.enabled).isFalse
        assertThat(user.role).isNull()
    }

    @Test
    fun `When sign up, then new user's identifier is created`() {
        val signUp = SignUp("Joshua", "Ivanov", "+79210017007")
        userService.signUp(signUp)

        val identifiers = identifierRepository.findAll()
        assertThat(identifiers).hasSize(1)
        val identifier = identifiers[0]

        val users = userRepository.findAll()
        assertThat(users).hasSize(1)
        val user = users[0]

        assertThat(identifier.type).isEqualTo(Identifier.Type.PHONE)
        assertThat(identifier.value).isEqualTo("+79210017007")
        assertThat(identifier.verified).isFalse
        assertThat(identifier.verificationCode).matches("\\d{6}")
        assertThat(identifier.user).isEqualTo(user)
    }

    @Test
    fun `When sign up, then verification SMS is sent`() {
        val signUp = SignUp("Joshua", "Ivanov", "+79210017007")
        userService.signUp(signUp)

        val identifiers = identifierRepository.findAll()
        assertThat(identifiers).hasSize(1)
        val code = identifiers[0].verificationCode

        val expectedText = "Для продолжения регистрации на сервисе MyRest введите код подтверждения: $code"
        verify(smsService, times(1)).send("+79210017007", expectedText)
    }

    @TestConfiguration
    @ComponentScan
    open class Config
}
