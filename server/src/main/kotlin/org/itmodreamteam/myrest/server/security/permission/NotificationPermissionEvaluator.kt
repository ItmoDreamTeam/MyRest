package org.itmodreamteam.myrest.server.security.permission

import org.itmodreamteam.myrest.server.repository.messaging.NotificationRepository
import org.itmodreamteam.myrest.server.security.UserAuthentication
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class NotificationPermissionEvaluator(
    private val notificationRepository: NotificationRepository,
) : DomainPermissionEvaluator {

    override val targetType: String = "Notification"

    override fun hasPermission(authentication: UserAuthentication, targetId: Long, permission: String): Boolean {
        val notification = notificationRepository.findByIdOrNull(targetId) ?: return false
        return authentication.principal.id == notification.user.id
    }
}
