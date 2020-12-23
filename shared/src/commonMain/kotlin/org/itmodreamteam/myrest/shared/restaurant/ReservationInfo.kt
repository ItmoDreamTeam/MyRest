package org.itmodreamteam.myrest.shared.restaurant

import kotlinx.datetime.LocalDateTime
import org.itmodreamteam.myrest.shared.table.TableView
import org.itmodreamteam.myrest.shared.user.Profile

data class ReservationInfo(
    val id: Long,
    val user: Profile,
    val table: TableView,
    val manager: EmployeeInfo?,
    val status: ReservationStatus,
    val activeFrom: LocalDateTime,
    val activeUntil: LocalDateTime
)