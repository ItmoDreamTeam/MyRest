package org.itmodreamteam.myrest.shared.user

data class Profile(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val enabled: Boolean,
    val locked: Boolean,
    val role: Role?,
)
