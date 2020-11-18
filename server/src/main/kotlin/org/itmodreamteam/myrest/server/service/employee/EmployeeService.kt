package org.itmodreamteam.myrest.server.service.employee

import org.itmodreamteam.myrest.shared.restaurant.EmployeeInfo
import org.itmodreamteam.myrest.shared.restaurant.EmployeeInvitation

interface EmployeeService {

    fun inviteEmployee(restaurantId: Long, invitation: EmployeeInvitation): EmployeeInfo
}
