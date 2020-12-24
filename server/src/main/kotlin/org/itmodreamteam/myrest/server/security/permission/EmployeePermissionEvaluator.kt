package org.itmodreamteam.myrest.server.security.permission

import org.itmodreamteam.myrest.server.security.UserAuthentication
import org.itmodreamteam.myrest.server.service.employee.EmployeeService
import org.itmodreamteam.myrest.shared.restaurant.EmployeePosition
import org.springframework.stereotype.Component

@Component
class EmployeePermissionEvaluator(
    private val employeeService: EmployeeService,
) : DomainPermissionEvaluator {

    override val targetType: String = "Employee"

    override fun hasPermission(authentication: UserAuthentication, targetId: Long, permission: String): Boolean {
        val employee = employeeService.getById(targetId)
        return when (permission) {
            "write-user" -> employee.user == authentication.profile
            "write-manager" -> {
                val activeManagers = employeeService.getEmployeesOfRestaurant(employee.restaurant.id)
                    .filter { it.position == EmployeePosition.MANAGER && it.active }
                    .map { it.user }
                return authentication.principal in activeManagers
            }
            else -> false
        }
    }
}
