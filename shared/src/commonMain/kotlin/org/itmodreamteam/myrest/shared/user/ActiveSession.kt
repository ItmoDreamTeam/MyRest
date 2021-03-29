package org.itmodreamteam.myrest.shared.user

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.itmodreamteam.myrest.shared.config.kotlinx.LocalDateTimeSerializer

@Serializable
data class ActiveSession(
    val id: Long,

    @Serializable(with = LocalDateTimeSerializer::class)
    val created: LocalDateTime,

    val token: String,
)
