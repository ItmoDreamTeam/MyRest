package org.itmodreamteam.myrest.shared.restaurant

import kotlinx.serialization.Serializable

@Serializable
data class RestaurantUpdateInfo(
    val name: String? = null,
    val description: String? = null,
    val legalInfo: String? = null,
    val websiteUrl: String? = null,
    val phone: String? = null,
    val email: String? = null,
) {
    val containsUpdate: Boolean
        get() = listOf(name, description, legalInfo, websiteUrl, phone, email).any { !it.isNullOrBlank() }
}
