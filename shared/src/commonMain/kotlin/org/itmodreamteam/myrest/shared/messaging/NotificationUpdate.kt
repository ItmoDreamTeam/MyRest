package org.itmodreamteam.myrest.shared.messaging

import kotlinx.serialization.Serializable

@Serializable
data class NotificationUpdate(
    val seen: Boolean,
)
