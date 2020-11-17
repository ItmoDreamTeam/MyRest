package org.itmodreamteam.myrest.server.repository.restaurant

import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.repository.JpaEntityRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface RestaurantRepository : JpaEntityRepository<Restaurant> {

    fun findByNameLike(nameLike: String, descriptionLike: String, pageable: Pageable): Page<Restaurant>
    fun findByName(name: String): Restaurant?
}
