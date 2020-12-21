package org.itmodreamteam.myrest.shared.restaurant

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
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
    @Transient
    val active: Boolean = userStatus == EmployeeUserStatus.ACTIVE && restaurantStatus == EmployeeRestaurantStatus.ACTIVE
}
