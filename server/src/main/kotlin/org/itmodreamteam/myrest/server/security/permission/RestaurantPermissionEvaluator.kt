package org.itmodreamteam.myrest.server.security.permission

import org.itmodreamteam.myrest.server.security.UserAuthentication
import org.itmodreamteam.myrest.server.service.employee.EmployeeService
import org.itmodreamteam.myrest.shared.restaurant.EmployeePosition
import org.springframework.stereotype.Component

@Component
class RestaurantPermissionEvaluator(
    private val employeeService: EmployeeService,
) : DomainPermissionEvaluator {

    override val targetType: String = "Restaurant"

    override fun hasPermission(authentication: UserAuthentication, targetId: Long, permission: String): Boolean {
        val activeManagers = employeeService.getEmployeesOfRestaurant(targetId)
            .filter { it.position == EmployeePosition.MANAGER && it.active() }
            .map { it.user }
        return authentication.principal in activeManagers
    }
}
