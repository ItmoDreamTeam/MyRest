package org.itmodreamteam.myrest.server.service.table

import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Employee
import org.itmodreamteam.myrest.server.model.restaurant.RestaurantTable
import org.itmodreamteam.myrest.server.model.restaurant.Waiter
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantTableRepository
import org.itmodreamteam.myrest.server.repository.restaurant.WaiterRepository
import org.itmodreamteam.myrest.server.view.assembler.ModelViewAssembler
import org.itmodreamteam.myrest.shared.restaurant.EmployeeInfo
import org.itmodreamteam.myrest.shared.table.TableView
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TableWaiterServiceImpl(
    private val tableRepository: RestaurantTableRepository,
    private val waiterRepository: WaiterRepository,
    private val tableViewAssembler: ModelViewAssembler<RestaurantTable, TableView>,
    private val employeeToEmployeeInfoAssembler: ModelViewAssembler<Employee, EmployeeInfo>
) : TableWaiterService {

    override fun addWaiter(tableId: Long, waiterId: Long): EmployeeInfo {
        val table = getRestaurantTableEntity(tableId)
        val waiter = getWaiterEntity(waiterId)
        if (waiter.restaurant != table.restaurant) {
            throw UserException("employee.waiter.not-found")
        }
        table.addWaiter(waiter)
        tableRepository.save(table)
        return employeeToEmployeeInfoAssembler.toView(waiter)
    }

    override fun removeWaiter(tableId: Long, waiterId: Long): EmployeeInfo {
        val table = getRestaurantTableEntity(tableId)
        val waiter = getWaiterEntity(waiterId)
        table.removeWaiter(waiter)
        tableRepository.save(table)
        return employeeToEmployeeInfoAssembler.toView(waiter)
    }

    override fun getTableWaiters(tableId: Long): List<EmployeeInfo> {
        val table = getRestaurantTableEntity(tableId)
        return employeeToEmployeeInfoAssembler.toViewList(table.waiters)
    }

    override fun getWaiterTables(waiterId: Long): List<TableView> {
        val waiter = getWaiterEntity(waiterId)
        val tables = tableRepository.findByRestaurant(waiter.restaurant)
        val waiterTables = tables.filter { waiter in it.waiters }
        return tableViewAssembler.toViewList(waiterTables)
    }

    private fun getRestaurantTableEntity(id: Long): RestaurantTable {
        return tableRepository.findByIdOrNull(id)
            ?: throw UserException("table.not-found")
    }

    private fun getWaiterEntity(id: Long): Waiter {
        return waiterRepository.findByIdOrNull(id)
            ?: throw UserException("employee.waiter.not-found")
    }
}
