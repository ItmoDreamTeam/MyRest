package org.itmodreamteam.myrest.server.controller

import org.itmodreamteam.myrest.server.service.employee.EmployeeService
import org.itmodreamteam.myrest.shared.restaurant.EmployeeInfo
import org.itmodreamteam.myrest.shared.restaurant.EmployeeInvitation
import org.itmodreamteam.myrest.shared.restaurant.EmployeeRestaurantStatus
import org.itmodreamteam.myrest.shared.restaurant.EmployeeUserStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
class EmployeeController(private val employeeService: EmployeeService) {

    @GetMapping("/employees/{id}")
    @PreAuthorize("isAuthenticated()")
    fun getById(@PathVariable id: Long): EmployeeInfo {
        // TODO permissions
        return employeeService.getById(id)
    }

    @GetMapping("/restaurants/{restaurantId}/employees")
    @PreAuthorize("hasPermission(#restaurantId, 'Restaurant', 'read')")
    fun getEmployeesOfRestaurant(@PathVariable restaurantId: Long): List<EmployeeInfo> {
        return employeeService.getEmployeesOfRestaurant(restaurantId)
    }

    @PutMapping("/restaurants/{restaurantId}/employees")
    @PreAuthorize("hasPermission(#restaurantId, 'Restaurant', 'write')")
    fun inviteEmployee(@PathVariable restaurantId: Long, @RequestBody invitation: EmployeeInvitation): EmployeeInfo {
        return employeeService.inviteEmployee(restaurantId, invitation)
    }

    @PutMapping("/employees/{id}")
    @PreAuthorize("hasPermission(#id, 'Employee', 'write-user')")
    fun updateEmployee(@PathVariable id: Long, @RequestParam status: EmployeeUserStatus): EmployeeInfo {
        return employeeService.updateEmployee(id, status)
    }

    @PutMapping("/employees/{id}/manager")
    @PreAuthorize("hasPermission(#id, 'Employee', 'write-manager')")
    fun updateEmployee(@PathVariable id: Long, @RequestParam status: EmployeeRestaurantStatus): EmployeeInfo {
        return employeeService.updateEmployee(id, status)
    }
}
