package org.itmodreamteam.myrest.shared.messaging

import kotlinx.datetime.LocalDateTime

data class NotificationView(
    val id: Long,
    val created: LocalDateTime,
    val content: NotificationContent,
    val seen: Boolean,
)
