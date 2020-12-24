package org.itmodreamteam.myrest.server.controller

import org.itmodreamteam.myrest.server.service.table.TableService
import org.itmodreamteam.myrest.shared.table.TableInfo
import org.itmodreamteam.myrest.shared.table.TableView
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
class RestaurantTableController(private val tableService: TableService) {

    @GetMapping("/restaurants/{restaurantId}/tables")
    fun getRestaurantTables(@PathVariable restaurantId: Long): List<TableView> {
        return tableService.getRestaurantTables(restaurantId)
    }

    @PutMapping("/restaurants/{restaurantId}/tables")
    @PreAuthorize("hasPermission(#restaurantId, 'Restaurant', 'write')")
    fun addTable(@PathVariable restaurantId: Long, @RequestBody info: TableInfo): TableView {
        return tableService.addTable(restaurantId, info)
    }

    @PutMapping("/tables/{tableId}")
    @PreAuthorize("hasPermission(#tableId, 'Table', 'write')")
    fun updateTable(@PathVariable tableId: Long, @RequestBody info: TableInfo): TableView {
        return tableService.updateTable(tableId, info)
    }

    @DeleteMapping("/tables/{tableId}")
    @PreAuthorize("hasPermission(#tableId, 'Table', 'write')")
    fun removeTable(@PathVariable tableId: Long): TableView {
        return tableService.removeTable(tableId)
    }
}
