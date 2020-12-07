package org.itmodreamteam.myrest.shared.restaurant

import org.itmodreamteam.myrest.shared.ContentPage
import org.itmodreamteam.myrest.shared.Pageable

interface RestaurantClient {

    suspend fun search(keyword: String, pageable: Pageable): ContentPage<RestaurantInfo>

    suspend fun register(newRestaurant: RestaurantRegistrationInfo): RestaurantInfo

    suspend fun update(id: Long, updateInfo: RestaurantUpdateInfo): RestaurantInfo

    suspend fun searchByAdmin(
        keyword: String,
        statuses: Set<RestaurantStatus>,
        pageable: Pageable
    ): ContentPage<RestaurantInfo>

    suspend fun updateStatusByAdmin(id: Long, status: RestaurantStatus): RestaurantInfo
}
