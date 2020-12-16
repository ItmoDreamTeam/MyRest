package org.itmodreamteam.myrest.server.service.table

import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.model.restaurant.RestaurantTable
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantTableRepository
import org.itmodreamteam.myrest.server.view.assembler.ModelViewAssembler
import org.itmodreamteam.myrest.shared.table.TableInfo
import org.itmodreamteam.myrest.shared.table.TableView
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TableServiceImpl(
    private val tableRepository: RestaurantTableRepository,
    private val restaurantRepository: RestaurantRepository,
    private val tableViewAssembler: ModelViewAssembler<RestaurantTable, TableView>
) : TableService {

    override fun addTable(restaurantId: Long, info: TableInfo): TableView {
        val restaurant = getRestaurantEntity(restaurantId)
        requireUniqueTableNumber(restaurant, info.number)
        val table = tableRepository.save(RestaurantTable(restaurant, info.number, info.description, info.numberOfSeats))
        return toTableView(table)
    }

    override fun updateTable(tableId: Long, info: TableInfo): TableView {
        val table = getRestaurantTableEntity(tableId)
        if (info.number != table.number) {
            requireUniqueTableNumber(table.restaurant, info.number)
        }
        table.number = info.number
        table.description = info.description
        table.numberOfSeats = info.numberOfSeats
        val updatedTable = tableRepository.save(table)
        return toTableView(updatedTable)
    }

    private fun requireUniqueTableNumber(restaurant: Restaurant, number: Int) {
        if (tableRepository.findByRestaurantAndNumber(restaurant, number) != null) {
            throw UserException("Столик с данным номером уже существует")
        }
    }

    override fun getTable(tableId: Long): TableView {
        val table = getRestaurantTableEntity(tableId)
        return toTableView(table)
    }

    override fun removeTable(tableId: Long): TableView {
        val table = getRestaurantTableEntity(tableId)
        tableRepository.delete(table)
        return toTableView(table)
    }

    override fun getRestaurantTables(restaurantId: Long): List<TableView> {
        val restaurant = getRestaurantEntity(restaurantId)
        return tableRepository.findByRestaurant(restaurant).map { toTableView(it) }
    }

    private fun toTableView(table: RestaurantTable): TableView {
        return tableViewAssembler.toView(table)
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
