package org.itmodreamteam.myrest.shared.restaurant

import org.itmodreamteam.myrest.shared.ContentPage
import org.itmodreamteam.myrest.shared.Pageable

interface RestaurantClient {

    suspend fun search(keyword: String, pageable: Pageable): ContentPage<RestaurantInfo>
}
