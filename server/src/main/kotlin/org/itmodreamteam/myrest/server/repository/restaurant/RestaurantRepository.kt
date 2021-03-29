package org.itmodreamteam.myrest.server.repository.restaurant

import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.repository.JpaEntityRepository
import org.itmodreamteam.myrest.shared.restaurant.RestaurantStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query

interface RestaurantRepository : JpaEntityRepository<Restaurant> {

    @Query(
        """
            SELECT restaurant from Restaurant restaurant
            where ((0 < LOCATE(UPPER(:keyword), UPPER(restaurant.name))) 
            or (0 < LOCATE(UPPER(:keyword), UPPER(restaurant.description)))) 
            and restaurant.status in :statuses
        """
    )
    fun find(keyword: String, statuses: List<RestaurantStatus>, pageable: Pageable): Page<Restaurant>

    fun findByName(name: String): Restaurant?
}
