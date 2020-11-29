package org.itmodreamteam.myrest.shared.table

import org.itmodreamteam.myrest.shared.restaurant.RestaurantInfo

data class TableView(
    val id: Long,
    val restaurant: RestaurantInfo,
    val info: TableInfo,
)
