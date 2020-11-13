package org.itmodreamteam.myrest.server.service.user

import kotlinx.datetime.toJavaLocalDateTime
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.within
import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.user.Identifier
import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.server.repository.user.IdentifierRepository
import org.itmodreamteam.myrest.server.repository.user.UserRepository
import org.itmodreamteam.myrest.server.service.sms.SmsService
import org.itmodreamteam.myrest.shared.user.SignInVerification
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDateTime.now
import java.time.temporal.ChronoUnit

@RunWith(SpringRunner::class)
@DataJpaTest
@ContextConfiguration(classes = [SessionTest.Config::class])
class SessionTest {

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var identifierRepository: IdentifierRepository

    lateinit var user: User
    lateinit var verificationCode: String

    @Before
    fun setUp() {
        user = userRepository.save(User("Alex", "Beaver"))
        val identifier = Identifier("+79210017007", Identifier.Type.PHONE, user)
        verificationCode = identifier.updateVerificationCode()
        identifierRepository.save(identifier)
    }

    @Test
    fun `When provide valid verification code, then session is started`() {
        val session = userService.startSession(SignInVerification("+79210017007", verificationCode))
        assertThat(session.token).isNotBlank
        assertThat(session.created.toJavaLocalDateTime()).isCloseTo(now(), within(1, ChronoUnit.SECONDS))
    }

    @Test
    fun `When provide valid verification code, then user is enabled`() {
        userService.startSession(SignInVerification("+79210017007", verificationCode))
        assertThat(user.enabled).isTrue
    }

    @Test(expected = UserException::class)
    fun `When provide invalid verification code, then failure`() {
        userService.startSession(SignInVerification("+79210017007", "123654"))
    }

    @Test(expected = UserException::class)
    fun `When provide non-existent phone, then failure`() {
        userService.startSession(SignInVerification("+79998888888", "123654"))
    }

    @TestConfiguration
    @ComponentScan
    @MockBean(SmsService::class)
    open class Config
}
