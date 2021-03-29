package org.itmodreamteam.myrest.server.service.user

import org.assertj.core.api.Assertions.assertThat
import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.user.Identifier
import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.server.repository.user.IdentifierRepository
import org.itmodreamteam.myrest.server.repository.user.UserRepository
import org.itmodreamteam.myrest.server.security.CurrentUserService
import org.itmodreamteam.myrest.server.service.sms.SmsService
import org.itmodreamteam.myrest.server.view.assembler.ModelViewAssembler
import org.itmodreamteam.myrest.shared.user.SignIn
import org.junit.Before
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
@ContextConfiguration(classes = [SignInTest.Config::class])
class SignInTest {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var identifierRepository: IdentifierRepository

    @MockBean
    lateinit var smsService: SmsService

    @MockBean
    lateinit var currentUserService: CurrentUserService

    lateinit var user: User
    lateinit var oldVerificationCode: String

    @Before
    fun setUp() {
        user = userRepository.save(User("Alex", "Beaver"))
        val identifier = Identifier("+79210017007", Identifier.Type.PHONE, user)
        oldVerificationCode = identifier.updateVerificationCode()
        identifierRepository.save(identifier)
    }

    @Test
    fun `Given existing user, when sign in, then new verification code is generated`() {
        userService.signIn(SignIn("+79210017007"))

        val identifiers = identifierRepository.findAll()
        assertThat(identifiers).hasSize(1)

        val identifier = identifiers[0]
        assertThat(identifier.user).isEqualTo(user)
        assertThat(identifier.type).isEqualTo(Identifier.Type.PHONE)
        assertThat(identifier.value).isEqualTo("+79210017007")
        assertThat(identifier.verificationCode).matches("\\d{6}")
        assertThat(identifier.verificationCode).isNotEqualTo(oldVerificationCode)
    }

    @Test
    fun `Given existing user, when sign in, then verification SMS is sent`() {
        userService.signIn(SignIn("+79210017007"))

        val identifiers = identifierRepository.findAll()
        assertThat(identifiers).hasSize(1)
        val code = identifiers[0].verificationCode

        val expectedText = "Вход в MyRest. Код подтверждения: $code"
        verify(smsService, times(1)).send("+79210017007", expectedText)
    }

    @Test(expected = UserException::class)
    fun `Given existing locked user, when sign in, then failure`() {
        user.locked = true
        userService.signIn(SignIn("+79210017007"))
    }

    @Test(expected = UserException::class)
    fun `When sign in with non-existent phone, then failure`() {
        userService.signIn(SignIn("+79990009900"))
    }

    @TestConfiguration
    @ComponentScan(basePackageClasses = [Config::class, ModelViewAssembler::class])
    open class Config
}
