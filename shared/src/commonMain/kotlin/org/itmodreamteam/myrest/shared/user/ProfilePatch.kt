package org.itmodreamteam.myrest.shared.user

import kotlinx.serialization.Serializable

@Serializable
data class ProfilePatch(
    val firstName: String,
    val lastName: String
)