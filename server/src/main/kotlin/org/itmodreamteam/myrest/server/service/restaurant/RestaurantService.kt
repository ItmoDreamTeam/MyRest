package org.itmodreamteam.myrest.server.service.restaurant

import org.itmodreamteam.myrest.shared.restaurant.RestaurantInfo
import org.itmodreamteam.myrest.shared.restaurant.RestaurantRegistrationInfo
import org.itmodreamteam.myrest.shared.restaurant.RestaurantStatus
import org.itmodreamteam.myrest.shared.restaurant.RestaurantUpdateInfo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface RestaurantService {
    fun register(newRestaurant: RestaurantRegistrationInfo): RestaurantInfo

    fun update(id: Long, updateInfo: RestaurantUpdateInfo): RestaurantInfo

    fun getById(id: Long): RestaurantInfo

    fun search(keyword: String, statuses: List<RestaurantStatus>, pageable: Pageable): Page<RestaurantInfo>
}