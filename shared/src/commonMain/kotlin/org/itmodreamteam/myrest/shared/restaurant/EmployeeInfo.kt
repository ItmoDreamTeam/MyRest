package org.itmodreamteam.myrest.shared.restaurant

import org.itmodreamteam.myrest.shared.user.Profile

data class EmployeeInfo(
    val id: Long,
    val restaurant: RestaurantInfo,
    val user: Profile,
    val position: EmployeePosition,
    val userStatus: EmployeeUserStatus,
    val restaurantStatus: EmployeeRestaurantStatus,
)
