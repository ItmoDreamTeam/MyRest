package org.itmodreamteam.myrest.server.service.table

import org.itmodreamteam.myrest.server.model.restaurant.Employee
import org.itmodreamteam.myrest.server.model.restaurant.Manager
import org.itmodreamteam.myrest.server.model.restaurant.Waiter
import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.server.service.employee.EmployeeService
import org.itmodreamteam.myrest.server.service.restaurant.RestaurantService
import org.itmodreamteam.myrest.server.view.assembler.ModelViewAssembler
import org.itmodreamteam.myrest.shared.restaurant.*
import org.itmodreamteam.myrest.shared.user.Profile
import org.springframework.stereotype.Service

@Service
class EmployeeServiceMock(
    private val restaurantService: RestaurantService,
    private val userProfileAssembler: ModelViewAssembler<User, Profile>
) : EmployeeService {

    override fun toEmployeeInfo(employee: Employee): EmployeeInfo {
        val restaurant = restaurantService.toRestaurantInfo(employee.restaurant)
        val profile = userProfileAssembler.toView(employee.user)
        val position = when (employee) {
            is Manager -> EmployeePosition.MANAGER
            is Waiter -> EmployeePosition.WAITER
            else -> throw IllegalStateException()
        }
        return EmployeeInfo(
            employee.id,
            restaurant,
            profile,
            position,
            employee.userStatus,
            employee.restaurantStatus
        )
    }

    override fun inviteEmployee(restaurantId: Long, invitation: EmployeeInvitation): EmployeeInfo {
        TODO("Not yet implemented")
    }

    override fun updateEmployee(id: Long, userStatus: EmployeeUserStatus): EmployeeInfo {
        TODO("Not yet implemented")
    }

    override fun updateEmployee(id: Long, restaurantStatus: EmployeeRestaurantStatus): EmployeeInfo {
        TODO("Not yet implemented")
    }

    override fun getRestaurantsOfUser(userId: Long): List<EmployeeInfo> {
        TODO("Not yet implemented")
    }

    override fun getEmployeesOfRestaurant(restaurantId: Long): List<EmployeeInfo> {
        TODO("Not yet implemented")
    }

    override fun getById(id: Long): EmployeeInfo {
        TODO("Not yet implemented")
    }
}
