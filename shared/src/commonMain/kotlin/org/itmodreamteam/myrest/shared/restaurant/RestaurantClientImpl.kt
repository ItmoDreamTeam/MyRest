package org.itmodreamteam.myrest.shared.restaurant

import io.ktor.client.request.*
import org.itmodreamteam.myrest.shared.AccessTokenProvider.Companion.provideAccessToken
import org.itmodreamteam.myrest.shared.ContentPage
import org.itmodreamteam.myrest.shared.HttpClientProvider
import org.itmodreamteam.myrest.shared.Pageable

@ExperimentalStdlibApi
class RestaurantClientImpl : RestaurantClient {

    private val client = HttpClientProvider.provide()

    override suspend fun search(keyword: String, pageable: Pageable): ContentPage<RestaurantInfo> {
        return client.get {
            url("/restaurants")
            parameter("keyword", keyword)
            parameter("pageNumber", pageable.pageNumber)
            parameter("pageSize", pageable.pageSize)
        }
    }

    override suspend fun register(newRestaurant: RestaurantRegistrationInfo): RestaurantInfo {
        return client.put {
            url("/restaurants")
            provideAccessToken()
            body = newRestaurant
        }
    }

    override suspend fun update(id: Long, updateInfo: RestaurantUpdateInfo): RestaurantInfo {
        return client.put {
            url("/restaurants/$id")
            provideAccessToken()
            body = updateInfo
        }
    }

    override suspend fun searchByAdmin(
        keyword: String,
        statuses: Set<RestaurantStatus>,
        pageable: Pageable
    ): ContentPage<RestaurantInfo> {
        return client.get {
            url("/restaurants/all")
            parameter("keyword", keyword)
            parameter("statuses", statuses.joinToString())
            parameter("pageNumber", pageable.pageNumber)
            parameter("pageSize", pageable.pageSize)
            provideAccessToken()
        }
    }

    override suspend fun updateStatusByAdmin(id: Long, status: RestaurantStatus): RestaurantInfo {
        return client.put {
            url("/restaurants/$id/status")
            parameter("status", status)
            provideAccessToken()
        }
    }
}
