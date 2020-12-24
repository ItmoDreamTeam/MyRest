package org.itmodreamteam.myrest.server.security.permission

import org.itmodreamteam.myrest.server.security.UserAuthentication
import org.itmodreamteam.myrest.server.service.employee.EmployeeService
import org.itmodreamteam.myrest.server.service.table.TableService
import org.itmodreamteam.myrest.shared.restaurant.EmployeePosition
import org.springframework.stereotype.Component

@Component
class TablePermissionEvaluator(
    private val employeeService: EmployeeService,
    private val tableService: TableService,
) : DomainPermissionEvaluator {

    override val targetType: String = "Table"

    override fun hasPermission(authentication: UserAuthentication, targetId: Long, permission: String): Boolean {
        val table = tableService.getTable(targetId)
        return when (permission) {
            "write" -> {
                val restaurant = table.restaurant
                val activeManagers = employeeService.getEmployeesOfRestaurant(restaurant.id)
                    .filter { it.position == EmployeePosition.MANAGER && it.active }
                    .map { it.user }
                return authentication.principal in activeManagers
            }
            else -> false
        }
    }
}
