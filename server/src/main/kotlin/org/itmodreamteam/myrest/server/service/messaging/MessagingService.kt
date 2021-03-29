package org.itmodreamteam.myrest.server.service.messaging

import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.shared.messaging.MessagingTokenRegistration

interface MessagingService {

    fun registerMessagingToken(user: User, registration: MessagingTokenRegistration)
}
