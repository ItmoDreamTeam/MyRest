package org.itmodreamteam.myrest.server.service.restaurant

import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.shared.restaurant.RestaurantRegistrationInfo
import org.itmodreamteam.myrest.shared.restaurant.RestaurantUpdateInfo
import org.springframework.data.domain.Page

interface RestaurantService {

    fun register(newRestaurant: RestaurantRegistrationInfo)
    fun update(updatedRestaurant: RestaurantUpdateInfo)
    fun getById(id: Long) : Restaurant
    fun search(keyword: String) : Page<Restaurant>
}