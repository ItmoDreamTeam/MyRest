package org.itmodreamteam.myrest.server.service.restaurant

import org.itmodreamteam.myrest.shared.restaurant.RestaurantInfo

interface RestaurantService {

    fun registerRestaurant(info: RestaurantInfo)
}