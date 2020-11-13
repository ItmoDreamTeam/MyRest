package org.itmodreamteam.myrest.shared.user

data class ActiveSession(
    val id: Long,
    val created: String,
    val token: String,
)
