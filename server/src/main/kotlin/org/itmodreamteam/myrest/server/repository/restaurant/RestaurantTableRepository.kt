package org.itmodreamteam.myrest.server.repository.restaurant

import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.model.restaurant.RestaurantTable
import org.itmodreamteam.myrest.server.repository.JpaEntityRepository

interface RestaurantTableRepository : JpaEntityRepository<RestaurantTable> {

    fun findByRestaurant(restaurant: Restaurant): List<RestaurantTable>
}
