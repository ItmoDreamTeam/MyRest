package org.itmodreamteam.myrest.shared.table

import kotlinx.serialization.Serializable
import org.itmodreamteam.myrest.shared.restaurant.RestaurantInfo

@Serializable
data class TableView(
    val id: Long,
    val restaurant: RestaurantInfo,
    val info: TableInfo,
)
