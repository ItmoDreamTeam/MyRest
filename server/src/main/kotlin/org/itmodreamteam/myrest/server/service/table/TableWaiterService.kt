package org.itmodreamteam.myrest.server.service.table

import org.itmodreamteam.myrest.shared.restaurant.EmployeeInfo
import org.itmodreamteam.myrest.shared.table.TableView

interface TableWaiterService {

    fun addWaiter(tableId: Long, waiterId: Long): EmployeeInfo

    fun removeWaiter(tableId: Long, waiterId: Long)

    fun getTableWaiters(tableId: Long): List<EmployeeInfo>

    fun getWaiterTables(waiterId: Long): List<TableView>
}
