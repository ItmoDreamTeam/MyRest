package org.itmodreamteam.myrest.server.service.reservation

import org.itmodreamteam.myrest.shared.restaurant.ReservationInfo
import java.time.LocalDate
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

    fun getReservationsOfRestaurant(restaurantId: Long, date: LocalDate): List<ReservationInfo>

    fun getReservationsOfUser(userId: Long, date: LocalDate): List<ReservationInfo>
}