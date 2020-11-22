package org.itmodreamteam.myrest.server.service.notification

import org.itmodreamteam.myrest.server.model.user.User

interface NotificationService {

    fun notify(user: User, text: String)
}
