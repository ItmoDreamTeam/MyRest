package org.itmodreamteam.myrest.server.service.user

import kotlinx.datetime.LocalDateTime
import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.user.Identifier
import org.itmodreamteam.myrest.server.model.user.Session
import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.server.repository.user.IdentifierRepository
import org.itmodreamteam.myrest.server.repository.user.SessionRepository
import org.itmodreamteam.myrest.server.repository.user.UserRepository
import org.itmodreamteam.myrest.server.service.sms.SmsService
import org.itmodreamteam.myrest.server.view.assembler.ModelViewAssembler
import org.itmodreamteam.myrest.shared.user.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val identifierRepository: IdentifierRepository,
    private val sessionRepository: SessionRepository,
    private val smsService: SmsService,
    private val userToProfileAssembler: ModelViewAssembler<User, Profile>
) : UserService {

    override fun signUp(signUp: SignUp) {
        val identifier = getSignUpIdentifier(signUp)
        sendVerificationMessage(identifier)
    }

    private fun getSignUpIdentifier(signUp: SignUp): Identifier {
        val identifier = identifierRepository.findByValue(signUp.phone)
        if (identifier == null) {
            val user = userRepository.save(User(signUp.firstName, signUp.lastName))
            return identifierRepository.save(Identifier(signUp.phone, Identifier.Type.PHONE, user))
        }
        val existingUser = identifier.user
        if (!existingUser.enabled) {
            existingUser.firstName = signUp.firstName
            existingUser.lastName = signUp.lastName
            userRepository.save(existingUser)
            return identifier
        }
        throw UserException("Пользователь с данным номером телефона уже зарегистрирован")
    }

    private fun sendVerificationMessage(identifier: Identifier) {
        val verificationCode = identifier.updateVerificationCode()
        val text = "Для продолжения регистрации на сервисе MyRest введите код подтверждения: $verificationCode"
        smsService.send(identifier.value, text)
    }

    override fun signIn(signIn: SignIn) {
        val identifier = identifierRepository.findByValue(signIn.phone)
            ?: throw UserException("Номер телефона не найдён")
        if (identifier.user.locked) {
            throw UserException("Аккаунт заблокирован. Обратитесь к администратору")
        }
        val verificationCode = identifier.updateVerificationCode()
        val text = "Вход в MyRest. Код подтверждения: $verificationCode"
        smsService.send(identifier.value, text)
    }

    override fun startSession(signInVerification: SignInVerification): ActiveSession {
        val identifier = identifierRepository.findByValue(signInVerification.phone)
            ?: throw UserException("Ошибка авторизации. Пожалуйста, попробуйте снова")
        identifier.verify(signInVerification.code)
        val user = identifier.user
        user.enabled = true
        userRepository.save(user)
        val session = sessionRepository.save(Session(user))
        return ActiveSession(session.id, LocalDateTime.parse(session.created.toString()), session.token)
    }

    override fun verifySession(token: String): Profile {
        val session = sessionRepository.findByToken(token)
            ?: throw UserException("Время сессии истекло. Пожалуйста, авторизуйтесь снова")
        val user = session.user
        if (user.locked) {
            throw UserException("Аккаунт заблокирован. Обратитесь к администратору")
        }
        if (!user.enabled) {
            throw UserException("Время сессии истекло. Пожалуйста, авторизуйтесь снова")
        }
        if (!session.active) {
            throw UserException("Время сессии истекло. Пожалуйста, авторизуйтесь снова")
        }
        return userToProfileAssembler.toView(user)
    }
}
