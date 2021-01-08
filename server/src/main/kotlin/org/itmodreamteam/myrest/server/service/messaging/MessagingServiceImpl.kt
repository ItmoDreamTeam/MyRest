package org.itmodreamteam.myrest.server.service.messaging

import org.itmodreamteam.myrest.server.model.messaging.MessagingToken
import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.server.repository.messaging.MessagingTokenRepository
import org.itmodreamteam.myrest.shared.messaging.MessagingTokenRegistration
import org.springframework.stereotype.Service

@Service
class MessagingServiceImpl(
    private val messagingTokenRepository: MessagingTokenRepository,
) : MessagingService {

    override fun registerMessagingToken(user: User, registration: MessagingTokenRegistration) {
        val exists = messagingTokenRepository.findByValue(registration.token) != null
        if (!exists) {
            messagingTokenRepository.save(MessagingToken(registration.token, user))
        }
    }
}
