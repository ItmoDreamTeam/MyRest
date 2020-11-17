package org.itmodreamteam.myrest.server.service.restaurant

import org.itmodreamteam.myrest.shared.restaurant.RegisterRestaurant
import org.itmodreamteam.myrest.shared.restaurant.UpdateRestaurant

interface RestaurantService {

    fun register(newRestaurant: RegisterRestaurant)
    fun update(updatedRestaurant: UpdateRestaurant)
    fun getById(id: Long)
}