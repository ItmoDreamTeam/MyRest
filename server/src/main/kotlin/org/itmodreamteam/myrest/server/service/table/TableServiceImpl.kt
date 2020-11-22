package org.itmodreamteam.myrest.server.service.table

import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.model.restaurant.RestaurantTable
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantTableRepository
import org.itmodreamteam.myrest.server.service.restaurant.RestaurantService
import org.itmodreamteam.myrest.shared.table.TableInfo
import org.itmodreamteam.myrest.shared.table.TableView
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TableServiceImpl(
    private val tableRepository: RestaurantTableRepository,
    private val restaurantRepository: RestaurantRepository,
    private val restaurantService: RestaurantService,
) : TableService {

    override fun addTable(restaurantId: Long, info: TableInfo): TableView {
        val restaurant = getRestaurantEntity(restaurantId)
        val table = tableRepository.save(RestaurantTable(restaurant, info.name, info.description, info.numberOfSeats))
        return toTableView(table)
    }

    override fun updateTable(tableId: Long, info: TableInfo): TableView {
        val table = getRestaurantTableEntity(tableId)
        table.name = info.name
        table.description = info.description
        table.numberOfSeats = info.numberOfSeats
        val updatedTable = tableRepository.save(table)
        return toTableView(updatedTable)
    }

    override fun getTable(tableId: Long): TableView {
        val table = getRestaurantTableEntity(tableId)
        return toTableView(table)
    }

    override fun removeTable(tableId: Long) {
        val table = getRestaurantTableEntity(tableId)
        tableRepository.delete(table)
    }

    override fun getRestaurantTables(restaurantId: Long): List<TableView> {
        val restaurant = getRestaurantEntity(restaurantId)
        return tableRepository.findByRestaurant(restaurant).map { toTableView(it) }
    }

    override fun toTableView(table: RestaurantTable): TableView {
        val restaurant = restaurantService.toRestaurantInfo(table.restaurant)
        val info = TableInfo(table.name, table.description, table.numberOfSeats)
        return TableView(table.id, restaurant, info)
    }

    private fun getRestaurantEntity(id: Long): Restaurant {
        return restaurantRepository.findByIdOrNull(id)
            ?: throw UserException("Ресторан не найден")
    }

    private fun getRestaurantTableEntity(id: Long): RestaurantTable {
        return tableRepository.findByIdOrNull(id)
            ?: throw UserException("Столик не найден")
    }
}
