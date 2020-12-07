package org.itmodreamteam.myrest.server.repository.restaurant

import org.itmodreamteam.myrest.server.model.restaurant.Reservation
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.model.restaurant.RestaurantTable
import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.server.repository.JpaEntityRepository
import org.itmodreamteam.myrest.shared.restaurant.ReservationStatus
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime


interface ReservationRepository : JpaEntityRepository<Reservation> {
    // !(end1 <= start2 || end2 <= start1) = !(end1 <= start2) && !(end2 <= start1) = end1 > start2 && end2 > start1
    @Query("select r from Reservation r where r.table = :table and r.status in :statuses and r.activeUntil > :activeFrom and :activeUntil > r.activeFrom")
    fun findTableReservationsByStatusesAndTimeRangeOverlapping(
        @Param("table") table: RestaurantTable,
        @Param("statuses") statuses: List<ReservationStatus>,
        @Param("activeFrom") activeFrom: LocalDateTime,
        @Param("activeUntil") activeUntil: LocalDateTime
    ) : List<Reservation>

    fun findReservationsByUserAndStatus(user: User, status: ReservationStatus): List<Reservation>

    @Query("select r from Reservation r where r.table.restaurant = :restaurant and r.status = :status")
    fun findReservationsByRestaurantAndStatus(@Param("restaurant") restaurant: Restaurant, @Param("status") status: ReservationStatus): List<Reservation>
}