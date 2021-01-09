package org.itmodreamteam.myrest.shared.restaurant

import kotlinx.serialization.Serializable
import org.itmodreamteam.myrest.shared.user.Profile

@Serializable
data class EmployeeInfo(
    val id: Long,
    val restaurant: RestaurantInfo,
    val user: Profile,
    val position: EmployeePosition,
    val userStatus: EmployeeUserStatus,
    val restaurantStatus: EmployeeRestaurantStatus,
) {
    fun active(): Boolean =
        userStatus == EmployeeUserStatus.ACTIVE && restaurantStatus == EmployeeRestaurantStatus.ACTIVE
}
