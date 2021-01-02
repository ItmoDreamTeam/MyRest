package org.itmodreamteam.myrest.shared.restaurant

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import org.itmodreamteam.myrest.shared.config.kotlinx.LocalDateTimeSerializer
import org.itmodreamteam.myrest.shared.table.TableView
import org.itmodreamteam.myrest.shared.user.Profile

@Serializable
data class ReservationInfo(
    val id: Long,
    val user: Profile,
    val table: TableView,
    val manager: EmployeeInfo?,
    val status: ReservationStatus,

    @Serializable(with = LocalDateTimeSerializer::class)
    val activeFrom: LocalDateTime,

    @Serializable(with = LocalDateTimeSerializer::class)
    val activeUntil: LocalDateTime
)
