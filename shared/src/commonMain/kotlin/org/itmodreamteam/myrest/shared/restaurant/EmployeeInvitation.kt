package org.itmodreamteam.myrest.shared.restaurant

import kotlinx.serialization.Serializable

@Serializable
data class EmployeeInvitation(
    val phone: String,
    val position: EmployeePosition,
)
