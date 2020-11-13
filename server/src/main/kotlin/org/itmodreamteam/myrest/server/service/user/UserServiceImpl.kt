package org.itmodreamteam.myrest.server.service.user

import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.user.Identifier
import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.server.repository.user.IdentifierRepository
import org.itmodreamteam.myrest.server.repository.user.UserRepository
import org.itmodreamteam.myrest.server.service.sms.SmsService
import org.itmodreamteam.myrest.shared.user.SignIn
import org.itmodreamteam.myrest.shared.user.SignUp
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val identifierRepository: IdentifierRepository,
    private val smsService: SmsService,
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
        TODO("Not yet implemented")
    }
}
