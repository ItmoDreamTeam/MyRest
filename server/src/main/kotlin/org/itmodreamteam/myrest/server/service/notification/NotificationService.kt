package org.itmodreamteam.myrest.server.service.notification

import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.shared.messaging.NotificationContent

interface NotificationService {

    fun notify(user: User, content: NotificationContent)
}
