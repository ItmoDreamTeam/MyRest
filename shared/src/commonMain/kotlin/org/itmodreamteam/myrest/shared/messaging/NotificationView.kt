package org.itmodreamteam.myrest.shared.messaging

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.itmodreamteam.myrest.shared.config.kotlinx.LocalDateTimeSerializer

@Serializable
data class NotificationView(
    val id: Long,

    @Serializable(with = LocalDateTimeSerializer::class)
    val created: LocalDateTime,

    val content: NotificationContent,
    val seen: Boolean,
)
