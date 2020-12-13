package org.itmodreamteam.myrest.server.service.restaurant

import kotlinx.datetime.toKotlinLocalDateTime
import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Reservation
import org.itmodreamteam.myrest.server.repository.restaurant.ReservationRepository
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantTableRepository
import org.itmodreamteam.myrest.server.repository.user.UserRepository
import org.itmodreamteam.myrest.shared.restaurant.ReservationInfo
import org.itmodreamteam.myrest.shared.restaurant.ReservationStatus
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

@Service
class ReservationViewServiceImpl(
    private val reservationService: ReservationService,
    private val reservationRepository: ReservationRepository,
    private val restaurantTableRepository: RestaurantTableRepository,
    private val restaurantRepository: RestaurantRepository,
    private val userRepository: UserRepository,
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

    override fun getReservationsOfRestaurant(restaurantId: Long, date: LocalDate): List<ReservationInfo> {
        val restaurant = restaurantRepository.findByIdOrNull(restaurantId)
            ?: throw UserException("Ресторан не найден")
        val statuses = ReservationStatus.values().toList()
        val activeFrom = date.atStartOfDay()
        val activeUntil = date.plusDays(1).atStartOfDay()
        return restaurantTableRepository.findByRestaurant(restaurant).flatMap { table ->
            reservationRepository.findReservationsForTableByStatusesAndTimeRangeOverlapping(
                table, statuses, activeFrom, activeUntil
            )
        }.map { toReservationInfo(it) }
    }

    override fun getReservationsOfUser(userId: Long, date: LocalDate): List<ReservationInfo> {
        val user = userRepository.findByIdOrNull(userId)
            ?: throw UserException("Пользователь не найден")
        val statuses = ReservationStatus.values().toList()
        val activeFrom = date.atStartOfDay()
        val activeUntil = date.plusDays(1).atStartOfDay()
        return reservationRepository.findReservationsForUserByStatusesAndTimeRangeOverlapping(
            user, statuses, activeFrom, activeUntil
        ).map { toReservationInfo(it) }
    }

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