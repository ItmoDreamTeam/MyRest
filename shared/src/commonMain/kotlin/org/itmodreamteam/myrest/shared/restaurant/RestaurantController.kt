package org.itmodreamteam.myrest.shared.restaurant

import org.itmodreamteam.myrest.shared.ContentPage
import org.itmodreamteam.myrest.shared.Pageable

interface RestaurantController {

    suspend fun search(keyword: String, pageable: Pageable): ContentPage<RestaurantInfo>
}
