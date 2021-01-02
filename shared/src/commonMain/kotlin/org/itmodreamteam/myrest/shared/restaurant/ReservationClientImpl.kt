package org.itmodreamteam.myrest.shared.restaurant

import io.ktor.client.request.*
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.itmodreamteam.myrest.shared.AccessTokenProvider.Companion.provideAccessToken
import org.itmodreamteam.myrest.shared.HttpClientProvider

class ReservationClientImpl : ReservationClient {

    private val client = HttpClientProvider.provide()

    override suspend fun getReservationsOfRestaurant(restaurantId: Long, date: LocalDate): List<ReservationInfo> {
        return client.get {
            url("/restaurants/$restaurantId/reservations")
            parameter("date", date)
            provideAccessToken()
        }
    }

    override suspend fun getReservationsOfUser(date: LocalDate): List<ReservationInfo> {
        return client.get {
            url("/reservations")
            parameter("date", date)
            provideAccessToken()
        }
    }

    override suspend fun submitReservationForApproval(
        tableId: Long,
        activeFrom: LocalDateTime,
        activeUntil: LocalDateTime
    ): ReservationInfo {
        return client.put {
            url("/reservations")
            parameter("tableId", tableId)
            parameter("activeFrom", activeFrom)
            parameter("activeUntil", activeUntil)
            provideAccessToken()
        }
    }

    override suspend fun reject(reservationId: Long): ReservationInfo {
        return client.put {
            url("/reservations/$reservationId/reject")
            provideAccessToken()
        }
    }

    override suspend fun approve(reservationId: Long): ReservationInfo {
        return client.put {
            url("/reservations/$reservationId/approve")
            provideAccessToken()
        }
    }

    override suspend fun start(reservationId: Long): ReservationInfo {
        return client.put {
            url("/reservations/$reservationId/start")
            provideAccessToken()
        }
    }

    override suspend fun complete(reservationId: Long): ReservationInfo {
        return client.put {
            url("/reservations/$reservationId/complete")
            provideAccessToken()
        }
    }
}
