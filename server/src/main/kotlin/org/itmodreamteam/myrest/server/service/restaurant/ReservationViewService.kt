package org.itmodreamteam.myrest.server.service.restaurant

import org.itmodreamteam.myrest.shared.restaurant.ReservationInfo
import java.time.LocalDateTime

interface ReservationViewService {
    fun submitReservationForApproval(
        tableId: Long,
        activeFrom: LocalDateTime,
        activeUntil: LocalDateTime
    ): ReservationInfo

    fun reject(reservationId: Long): ReservationInfo

    fun approve(reservationId: Long): ReservationInfo

    fun start(reservationId: Long): ReservationInfo

    fun complete(reservationId: Long): ReservationInfo
}