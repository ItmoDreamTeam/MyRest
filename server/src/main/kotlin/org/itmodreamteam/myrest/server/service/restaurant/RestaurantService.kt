package org.itmodreamteam.myrest.server.service.restaurant

import org.itmodreamteam.myrest.shared.restaurant.RegisterRestaurant
import org.itmodreamteam.myrest.shared.restaurant.UpdateRestaurant

interface RestaurantService {

    fun registerRestaurant(newRestaurant: RegisterRestaurant)
    fun updateRestaurant(updatedRestaurant: UpdateRestaurant)
}