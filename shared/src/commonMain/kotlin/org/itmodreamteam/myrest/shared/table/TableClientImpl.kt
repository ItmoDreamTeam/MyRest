package org.itmodreamteam.myrest.shared.table

import io.ktor.client.request.*
import org.itmodreamteam.myrest.shared.AccessTokenProvider.Companion.provideAccessToken
import org.itmodreamteam.myrest.shared.HttpClientProvider

class TableClientImpl : TableClient {

    private val client = HttpClientProvider.provide()

    override suspend fun getRestaurantTables(restaurantId: Long): List<TableView> {
        return client.get {
            url("/restaurants/$restaurantId/tables")
        }
    }

    override suspend fun addTable(restaurantId: Long, info: TableInfo): TableView {
        return client.put {
            url("/restaurants/$restaurantId/tables")
            provideAccessToken()
            body = info
        }
    }

    override suspend fun updateTable(tableId: Long, info: TableInfo): TableView {
        return client.put {
            url("/tables/$tableId")
            provideAccessToken()
            body = info
        }
    }

    override suspend fun removeTable(tableId: Long): TableView {
        return client.delete {
            url("/tables/$tableId")
            provideAccessToken()
        }
    }
}
