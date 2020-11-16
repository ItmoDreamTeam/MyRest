package org.itmodreamteam.myrest.shared.user

data class SignInVerification(
    val phone: String,
    val code: String,
)
