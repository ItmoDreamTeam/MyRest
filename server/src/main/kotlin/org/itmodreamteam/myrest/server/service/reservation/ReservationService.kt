package org.itmodreamteam.myrest.server.service.reservation

import org.itmodreamteam.myrest.server.model.restaurant.Reservation
import org.itmodreamteam.myrest.server.model.restaurant.RestaurantTable
import java.time.LocalDateTime

interface ReservationService {
    fun submitReservationForApproval(
        table: RestaurantTable,
        activeFrom: LocalDateTime,
        activeUntil: LocalDateTime
    ): Reservation

    fun findOverlappingReservations(
        table: RestaurantTable,
        activeFrom: LocalDateTime,
        activeUntil: LocalDateTime
    ): List<Reservation>

    fun reject(reservation: Reservation): Reservation

    fun approve(reservation: Reservation): Reservation

    fun start(reservation: Reservation): Reservation

    fun complete(reservation: Reservation): Reservation
}