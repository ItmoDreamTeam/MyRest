package org.itmodreamteam.myrest.server.service.restaurant

import org.itmodreamteam.myrest.shared.restaurant.RegisterRestaurant

interface RestaurantService {

    fun registerRestaurant(info: RegisterRestaurant)
}