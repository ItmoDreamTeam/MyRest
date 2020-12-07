package org.itmodreamteam.myrest.shared.restaurant

import kotlinx.datetime.LocalDateTime

data class ReservationInfo(
    val id: Long,
    val userId: Long,
    val tableId: Long,
    val managerId: Long?,
    val status: ReservationStatus,
    val activeFrom: LocalDateTime,
    val activeUntil: LocalDateTime
)