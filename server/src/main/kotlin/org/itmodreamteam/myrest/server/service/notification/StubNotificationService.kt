package org.itmodreamteam.myrest.server.service.notification

import org.springframework.stereotype.Service

@Service
class StubNotificationService: NotificationService {
    override fun notify(message: String) {
        print("")
    }
} 