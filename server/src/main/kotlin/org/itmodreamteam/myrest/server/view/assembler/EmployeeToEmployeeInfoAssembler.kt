package org.itmodreamteam.myrest.server.view.assembler

import org.itmodreamteam.myrest.server.model.restaurant.Employee
import org.itmodreamteam.myrest.server.model.restaurant.Manager
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.model.restaurant.Waiter
import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.shared.restaurant.EmployeeInfo
import org.itmodreamteam.myrest.shared.restaurant.EmployeePosition
import org.itmodreamteam.myrest.shared.restaurant.RestaurantInfo
import org.itmodreamteam.myrest.shared.user.Profile
import org.springframework.stereotype.Component

@Component
class EmployeeToEmployeeInfoAssembler(
    private val userToProfileAssembler: ModelViewAssembler<User, Profile>,
    private val restaurantToRestaurantInfoAssembler: ModelViewAssembler<Restaurant, RestaurantInfo>,
) : ModelViewAssembler<Employee, EmployeeInfo> {
    override fun toView(model: Employee): EmployeeInfo {
        val restaurant = restaurantToRestaurantInfoAssembler.toView(model.restaurant)
        val profile = userToProfileAssembler.toView(model.user)
        val position = when (model) {
            is Manager -> EmployeePosition.MANAGER
            is Waiter -> EmployeePosition.WAITER
            else -> throw IllegalStateException()
        }
        return EmployeeInfo(
            model.id,
            restaurant,
            profile,
            position,
            model.userStatus,
            model.restaurantStatus
        )
    }
}