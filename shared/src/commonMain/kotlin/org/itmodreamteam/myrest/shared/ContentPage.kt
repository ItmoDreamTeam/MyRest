package org.itmodreamteam.myrest.shared

data class ContentPage<E>(
    val content: List<E>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalPages: Int,
    val totalElements: Int,
)
