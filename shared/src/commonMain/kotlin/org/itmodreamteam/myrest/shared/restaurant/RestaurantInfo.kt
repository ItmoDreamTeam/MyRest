package org.itmodreamteam.myrest.shared.restaurant

data class RestaurantInfo (
    val id: Long,
    val status: RestaurantStatus,
    val name: String,
    val description: String,
    val legalInfo: String,
    val websiteUrl: String?,
    val phone: String?,
    val email: String?,
    val internalRating: Double,
    val externalRating: Double,
)