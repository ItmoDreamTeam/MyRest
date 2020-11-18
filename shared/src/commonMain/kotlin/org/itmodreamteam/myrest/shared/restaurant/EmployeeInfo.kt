package org.itmodreamteam.myrest.shared.restaurant

data class EmployeeInfo(
    val id: Long,
    val restaurantId: Long, // TODO: replace with restaurant DTO
    val userId: Long, // TODO: replace with user DTO
    val position: EmployeePosition,
    val status: EmployeeStatus,
)
