package org.itmodreamteam.myrest.shared.user

import kotlinx.serialization.Serializable

@Serializable
data class SignIn(
    val phone: String,
)
