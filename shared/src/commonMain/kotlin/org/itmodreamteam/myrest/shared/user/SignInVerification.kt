package org.itmodreamteam.myrest.shared.user

import kotlinx.serialization.Serializable

@Serializable
data class SignInVerification(
    val phone: String,
    val code: String,
)
