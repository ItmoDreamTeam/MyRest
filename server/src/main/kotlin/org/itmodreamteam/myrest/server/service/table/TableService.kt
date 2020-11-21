package org.itmodreamteam.myrest.server.service.table

import org.itmodreamteam.myrest.server.model.restaurant.RestaurantTable
import org.itmodreamteam.myrest.shared.table.TableInfo
import org.itmodreamteam.myrest.shared.table.TableView

interface TableService {

    fun addTable(restaurantId: Long, info: TableInfo): TableView

    fun updateTable(tableId: Long, info: TableInfo): TableView

    fun getTable(tableId: Long): TableView

    fun removeTable(tableId: Long)

    fun getRestaurantTables(restaurantId: Long): List<TableView>

    fun toTableView(table: RestaurantTable): TableView
}
