package org.itmodreamteam.myrest.shared.restaurant

data class RestaurantRegistrationInfo (
    val name: String,
    val description: String,
    val legalInfo: String,
    val websiteUrl: String? = null,
    val phone: String? = null,
    val email: String? = null,
)