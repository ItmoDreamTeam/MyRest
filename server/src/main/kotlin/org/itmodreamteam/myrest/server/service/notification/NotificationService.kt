package org.itmodreamteam.myrest.server.service.notification

import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.shared.messaging.NotificationContent
import org.itmodreamteam.myrest.shared.messaging.NotificationUpdate
import org.itmodreamteam.myrest.shared.messaging.NotificationView
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface NotificationService {

    fun notify(user: User, content: NotificationContent)

    fun update(id: Long, update: NotificationUpdate): NotificationView

    fun getUserNotifications(user: User, pageable: Pageable): Page<NotificationView>
}
