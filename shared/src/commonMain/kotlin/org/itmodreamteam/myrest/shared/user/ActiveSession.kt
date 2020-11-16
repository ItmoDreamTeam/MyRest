package org.itmodreamteam.myrest.shared.user

import kotlinx.datetime.LocalDateTime

data class ActiveSession(
    val id: Long,
    val created: LocalDateTime,
    val token: String,
)
