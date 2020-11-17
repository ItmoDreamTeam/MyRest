package org.itmodreamteam.myrest.shared.restaurant

data class RestaurantUpdateInfo (
    val name: String,
    val description: String? = null,
    val legalInfo: String? = null,
    val websiteUrl: String? = null,
    val phone: String? = null,
    val email: String? = null, ) {
    fun getProperties() : List<String?> = listOf(description, legalInfo, websiteUrl, phone, email)
}