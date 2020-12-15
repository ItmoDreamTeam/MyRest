package org.itmodreamteam.myrest.server.view.assembler

import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.model.restaurant.RestaurantTable
import org.itmodreamteam.myrest.shared.restaurant.RestaurantInfo
import org.itmodreamteam.myrest.shared.table.TableInfo
import org.itmodreamteam.myrest.shared.table.TableView
import org.springframework.stereotype.Component

@Component
class TableToTableViewAssembler(
    private val restaurantToRestaurantInfoAssembler: ModelViewAssembler<Restaurant, RestaurantInfo>
) : ModelViewAssembler<RestaurantTable, TableView> {
    override fun toView(model: RestaurantTable): TableView {
        val restaurant = restaurantToRestaurantInfoAssembler.toView(model.restaurant)
        val info = TableInfo(model.number, model.description, model.numberOfSeats)
        return TableView(model.id, restaurant, info)
    }
}