package org.itmodreamteam.myrest.server.service.restaurant

import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.shared.restaurant.RestaurantRegistrationInfo
import org.itmodreamteam.myrest.shared.restaurant.RestaurantUpdateInfo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface RestaurantService {

    fun register(newRestaurant: RestaurantRegistrationInfo)
    fun update(restaurantUpdateInfo: RestaurantUpdateInfo)
    fun getById(id: Long) : Restaurant
    fun search(keyword: String, pageable: Pageable) : Page<Restaurant>
}