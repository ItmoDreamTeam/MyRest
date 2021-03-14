package org.itmodreamteam.myrest.shared.restaurant

import io.ktor.client.request.*
import org.itmodreamteam.myrest.shared.AccessTokenProvider.Companion.provideAccessToken
import org.itmodreamteam.myrest.shared.ContentPage
import org.itmodreamteam.myrest.shared.HttpClientProvider
import org.itmodreamteam.myrest.shared.Pageable
import org.itmodreamteam.myrest.shared.Pageable.Companion.addPageableParameters
import org.itmodreamteam.myrest.shared.RestaurantInfoContentPage

class RestaurantClientImpl : RestaurantClient {

    private val client = HttpClientProvider.provide()

    override suspend fun getById(id: Long): RestaurantInfo {
        return client.get {
            url("/restaurants/$id")
            provideAccessToken()
        }
    }

    override suspend fun search(keyword: String, pageable: Pageable): ContentPage<RestaurantInfo> {
        return client.get {
            url("/restaurants")
            parameter("keyword", keyword)
            addPageableParameters(pageable)
        }
    }

    override suspend fun searchNonGeneric(keyword: String, pageable: Pageable): RestaurantInfoContentPage {
        return client.get {
            url("/restaurants")
            parameter("keyword", keyword)
            addPageableParameters(pageable)
        }
    }

    override suspend fun register(newRestaurant: RestaurantRegistrationInfo): RestaurantInfo {
        return client.put {
            url("/restaurants")
            provideAccessToken()
            body = newRestaurant
        }
    }

    override suspend fun getRestaurantsOfUser(): List<EmployeeInfo> {
        return client.get {
            url("/restaurants/mine")
            provideAccessToken()
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
            addPageableParameters(pageable)
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
