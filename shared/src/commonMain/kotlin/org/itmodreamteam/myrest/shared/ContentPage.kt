package org.itmodreamteam.myrest.shared

import kotlinx.serialization.Serializable

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
