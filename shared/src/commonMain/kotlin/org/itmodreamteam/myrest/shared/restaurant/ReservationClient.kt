package org.itmodreamteam.myrest.shared.restaurant

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.itmodreamteam.myrest.shared.error.ClientException
import kotlin.coroutines.cancellation.CancellationException

interface ReservationClient {

    @Throws(CancellationException::class, ClientException::class)
    suspend fun getReservationsOfRestaurant(restaurantId: Long, date: LocalDate): List<ReservationInfo>

    @Throws(CancellationException::class, ClientException::class)
    suspend fun getReservationsOfUser(date: LocalDate): List<ReservationInfo>

    @Throws(CancellationException::class, ClientException::class)
    suspend fun submitReservationForApproval(
        tableId: Long,
        activeFrom: LocalDateTime,
        activeUntil: LocalDateTime
    ): ReservationInfo

    @Throws(CancellationException::class, ClientException::class)
    suspend fun reject(reservationId: Long): ReservationInfo

    @Throws(CancellationException::class, ClientException::class)
    suspend fun approve(reservationId: Long): ReservationInfo

    @Throws(CancellationException::class, ClientException::class)
    suspend fun start(reservationId: Long): ReservationInfo

    @Throws(CancellationException::class, ClientException::class)
    suspend fun complete(reservationId: Long): ReservationInfo
}
