package org.itmodreamteam.myrest.shared.restaurant

import io.ktor.client.request.*
import org.itmodreamteam.myrest.shared.AccessTokenProvider
import org.itmodreamteam.myrest.shared.ContentPage
import org.itmodreamteam.myrest.shared.HttpClientProvider
import org.itmodreamteam.myrest.shared.Pageable
import org.itmodreamteam.myrest.shared.Pageable.Companion.addPageableParameters
import org.itmodreamteam.myrest.shared.RestaurantInfoContentPage

class RestaurantClientImpl(private val accessTokenProvider: AccessTokenProvider) : RestaurantClient {

    private val client = HttpClientProvider.provide()

    override suspend fun getById(id: Long): RestaurantInfo {
        return client.get {
            url("/restaurants/$id")
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
            header("Authorization", "Bearer ${accessTokenProvider.accessToken}")
            body = newRestaurant
        }
    }

    override suspend fun getRestaurantsOfUser(): List<EmployeeInfo> {
        return client.get {
            url("/restaurants/mine")
            header("Authorization", "Bearer ${accessTokenProvider.accessToken}")
        }
    }

    override suspend fun update(id: Long, updateInfo: RestaurantUpdateInfo): RestaurantInfo {
        return client.put {
            url("/restaurants/$id")
            header("Authorization", "Bearer ${accessTokenProvider.accessToken}")
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
            header("Authorization", "Bearer ${accessTokenProvider.accessToken}")
        }
    }

    override suspend fun updateStatusByAdmin(id: Long, status: RestaurantStatus): RestaurantInfo {
        return client.put {
            url("/restaurants/$id/status")
            parameter("status", status)
            header("Authorization", "Bearer ${accessTokenProvider.accessToken}")
        }
    }
}
