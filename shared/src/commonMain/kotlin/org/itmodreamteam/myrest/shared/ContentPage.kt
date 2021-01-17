package org.itmodreamteam.myrest.shared

import kotlinx.serialization.Serializable
import org.itmodreamteam.myrest.shared.restaurant.RestaurantInfo

@Serializable
data class ContentPage<E>(
    val content: List<E>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalPages: Int,
    val totalElements: Int,
) {
    companion object {
        fun <E> empty(pageable: Pageable): ContentPage<E> {
            return ContentPage(emptyList(), pageable.pageNumber, pageable.pageSize, 0, 0)
        }
    }
}

@Serializable
data class RestaurantInfoContentPage(
    val content: List<RestaurantInfo>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalPages: Int,
    val totalElements: Int,
) {
    fun toGeneric(): ContentPage<RestaurantInfo> {
        return ContentPage(content, pageNumber, pageSize, totalPages, totalElements)
    }
}
