package org.itmodreamteam.myrest.shared.table

import org.itmodreamteam.myrest.shared.error.ClientException
import kotlin.coroutines.cancellation.CancellationException

interface TableClient {

    @Throws(CancellationException::class, ClientException::class)
    suspend fun getRestaurantTables(restaurantId: Long): List<TableView>

    @Throws(CancellationException::class, ClientException::class)
    suspend fun addTable(restaurantId: Long, info: TableInfo): TableView

    @Throws(CancellationException::class, ClientException::class)
    suspend fun updateTable(tableId: Long, info: TableInfo): TableView

    @Throws(CancellationException::class, ClientException::class)
    suspend fun removeTable(tableId: Long): TableView
}
