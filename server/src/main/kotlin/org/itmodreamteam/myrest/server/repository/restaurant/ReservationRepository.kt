package org.itmodreamteam.myrest.server.repository.restaurant

import org.itmodreamteam.myrest.server.model.restaurant.Reservation
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.model.restaurant.RestaurantTable
import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.server.repository.JpaEntityRepository
import org.itmodreamteam.myrest.shared.restaurant.ReservationStatus
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface ReservationRepository : JpaEntityRepository<Reservation> {
    @Query("select r from Reservation r where r.table = :table and r.status in :statuses and r.activeUntil > :activeFrom and :activeUntil > r.activeFrom")
    fun findReservationsForTableByStatusesAndTimeRangeOverlapping(
        table: RestaurantTable,
        statuses: List<ReservationStatus>,
        activeFrom: LocalDateTime,
        activeUntil: LocalDateTime
    ): List<Reservation>

    fun findReservationsByUserAndStatus(user: User, status: ReservationStatus): List<Reservation>

    @Query("select r from Reservation r where r.table.restaurant = :restaurant and r.status = :status")
    fun findReservationsByRestaurantAndStatus(restaurant: Restaurant, status: ReservationStatus): List<Reservation>
}