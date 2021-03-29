package org.itmodreamteam.myrest.shared.table

import kotlinx.serialization.Serializable

@Serializable
data class TableInfo(
    val number: Int,
    val description: String?,
    val numberOfSeats: Int,
)
