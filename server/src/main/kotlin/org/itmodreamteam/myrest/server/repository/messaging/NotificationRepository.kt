package org.itmodreamteam.myrest.server.repository.messaging

import org.itmodreamteam.myrest.server.model.messaging.Notification
import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.server.repository.JpaEntityRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface NotificationRepository : JpaEntityRepository<Notification> {

    fun findByUser(user: User, pageable: Pageable): Page<Notification>
}
