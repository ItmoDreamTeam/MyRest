package org.itmodreamteam.myrest.shared.restaurant

import io.ktor.client.*
import io.ktor.client.request.*
import org.itmodreamteam.myrest.shared.ContentPage
import org.itmodreamteam.myrest.shared.Pageable

class RestaurantControllerImpl(
    private val client: HttpClient,
) : RestaurantController {

    override suspend fun search(keyword: String, pageable: Pageable): ContentPage<RestaurantInfo> {
        return client.get {
            url("/restaurants")
            parameter("keyword", keyword)
            parameter("pageNumber", pageable.pageNumber)
            parameter("pageSize", pageable.pageSize)
        }
    }
}
