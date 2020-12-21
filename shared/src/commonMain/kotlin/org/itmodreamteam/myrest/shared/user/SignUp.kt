package org.itmodreamteam.myrest.shared.user

import kotlinx.serialization.Serializable

@Serializable
data class SignUp(
    val firstName: String,
    val lastName: String,
    val phone: String,
)
