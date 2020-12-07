package org.itmodreamteam.myrest.server.service.restaurant

import kotlinx.datetime.toKotlinLocalDateTime
import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Reservation
import org.itmodreamteam.myrest.server.repository.restaurant.ReservationRepository
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantTableRepository
import org.itmodreamteam.myrest.shared.restaurant.ReservationInfo
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ReservationViewServiceImpl(
    private val reservationService: ReservationService,
    private val reservationRepository: ReservationRepository,
    private val restaurantTableRepository: RestaurantTableRepository,
) : ReservationViewService {

    override fun submitReservationForApproval(
        tableId: Long,
        activeFrom: LocalDateTime,
        activeUntil: LocalDateTime
    ): ReservationInfo {
        // TODO create DAO layer with domain-specific error handling
        val table = restaurantTableRepository.findByIdOrNull(tableId) ?: throw UserException("Стол $tableId не найден")
        return toReservationInfo(reservationService.submitReservationForApproval(table, activeFrom, activeUntil))
    }

    override fun reject(reservationId: Long): ReservationInfo =
        toReservationInfo(reservationService.reject(getById(reservationId)))

    override fun approve(reservationId: Long): ReservationInfo =
        toReservationInfo(reservationService.approve(getById(reservationId)))

    override fun start(reservationId: Long): ReservationInfo =
        toReservationInfo(reservationService.start(getById(reservationId)))

    override fun complete(reservationId: Long): ReservationInfo =
        toReservationInfo(reservationService.complete(getById(reservationId)))

    private fun toReservationInfo(reservation: Reservation): ReservationInfo {
        return ReservationInfo(
            reservation.id,
            reservation.user.id,
            reservation.table.id,
            reservation.manager?.id,
            reservation.status,
            reservation.activeFrom.toKotlinLocalDateTime(),
            reservation.activeUntil.toKotlinLocalDateTime()
        )
    }

    private fun getById(id: Long): Reservation =
        reservationRepository.findByIdOrNull(id) ?: throw UserException("Бронь $id не найдена")
}