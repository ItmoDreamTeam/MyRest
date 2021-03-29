package org.itmodreamteam.myrest.shared

import io.ktor.client.request.*

data class Pageable(
    val pageNumber: Int,
    val pageSize: Int,
) {
    companion object {
        fun HttpRequestBuilder.addPageableParameters(pageable: Pageable) {
            parameter("page", pageable.pageNumber)
            parameter("size", pageable.pageSize)
        }
    }
}
