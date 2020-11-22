package org.itmodreamteam.myrest.server.service.notification

import org.itmodreamteam.myrest.server.model.user.User
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FakeNotificationService : NotificationService {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun notify(user: User, text: String) {
        log.info("Notifying user ID=${user.id}. Message: $text")
    }
}
