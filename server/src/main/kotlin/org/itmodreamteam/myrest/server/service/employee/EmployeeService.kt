package org.itmodreamteam.myrest.server.service.employee

import org.itmodreamteam.myrest.shared.restaurant.EmployeeInfo
import org.itmodreamteam.myrest.shared.restaurant.EmployeeInvitation
import org.itmodreamteam.myrest.shared.restaurant.EmployeeRestaurantStatus
import org.itmodreamteam.myrest.shared.restaurant.EmployeeUserStatus

interface EmployeeService {

    fun inviteEmployee(restaurantId: Long, invitation: EmployeeInvitation): EmployeeInfo

    fun updateEmployee(id: Long, userStatus: EmployeeUserStatus): EmployeeInfo

    fun updateEmployee(id: Long, restaurantStatus: EmployeeRestaurantStatus): EmployeeInfo

    fun getRestaurantsOfUser(userId: Long): List<EmployeeInfo>

    fun getEmployeesOfRestaurant(restaurantId: Long): List<EmployeeInfo>

    fun getById(id: Long): EmployeeInfo
}
