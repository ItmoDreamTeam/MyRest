package org.itmodreamteam.myrest.server.service.restaurant

import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Manager
import org.itmodreamteam.myrest.server.model.restaurant.Reservation
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.model.restaurant.RestaurantTable
import org.itmodreamteam.myrest.server.repository.restaurant.EmployeeRepository
import org.itmodreamteam.myrest.server.repository.restaurant.ReservationRepository
import org.itmodreamteam.myrest.server.security.CurrentUserService
import org.itmodreamteam.myrest.server.service.notification.NotificationService
import org.itmodreamteam.myrest.shared.restaurant.ReservationStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ReservationServiceImpl(
    private val reservationRepository: ReservationRepository,
    private val notificationService: NotificationService,
    private val currentUserService: CurrentUserService,
    private val employeeRepository: EmployeeRepository
) : ReservationService {

    override fun submitReservationForApproval(
        table: RestaurantTable,
        activeFrom: LocalDateTime,
        activeUntil: LocalDateTime
    ): Reservation {
        val overlapping = findOverlappingReservations(table, activeFrom, activeUntil)
        if (overlapping.isNotEmpty()) {
            throw UserException("Временной интервал $activeFrom - $activeUntil занят. Попробуйте другое время")
        }
        var reservation = Reservation(currentUserService.currentUserEntity, table, activeFrom, activeUntil)
        reservation = reservationRepository.save(reservation)
        /*
        TODO
        It's possible to have concurrent save operation which can lead to overlapping reservations (due to absence of something like time range overlapping constraint).
        Due to caching on application side it's not possible to guarantee consistency.
        Possible solutions are:
        - Trigger check on DB side
        - Batch job detecting overlapping reservations and notifying responsible managers
        - Nested transaction + check with possible fallback (need to setup transaction manager)
        */
        return reservation
    }

    override fun findOverlappingReservations(
        table: RestaurantTable,
        activeFrom: LocalDateTime,
        activeUntil: LocalDateTime
    ): List<Reservation> {
        return reservationRepository.findTableReservationsByStatusesAndTimeRangeOverlapping(
            table,
            listOf(ReservationStatus.PENDING, ReservationStatus.APPROVED, ReservationStatus.IN_PROGRESS),
            activeFrom, activeUntil
        )
    }

    override fun reject(reservation: Reservation): Reservation {
        // TODO should it be checked on security level?
        if (currentUserService.currentUserEntity != reservation.user) {
            throw UserException("Запрашиваемая бронь оформлена не на Вас")
        }
        return updateStatusAndPersist(reservation, ReservationStatus.REJECTED)
    }

    override fun approve(reservation: Reservation): Reservation {
        reservation.manager = getCurrentUserBeingManagerOf(reservation.table.restaurant)
        reservation.status = ReservationStatus.APPROVED
        val approved = reservationRepository.save(reservation)
        notificationService.notify(
            approved.user,
            "Ваша бронь была подтверждена. Ваш менеджер: ${reservation.manager}. " +
                    "Место: ${reservation.table.restaurant}, столик ${reservation.table.number}, " +
                    "время: ${reservation.activeFrom} - ${reservation.activeUntil}"
        )
        return reservation
    }

    override fun start(reservation: Reservation): Reservation {
        currentUserMustBeManagerOf(reservation)
        return updateStatusAndPersist(reservation, ReservationStatus.IN_PROGRESS)
    }

    override fun complete(reservation: Reservation): Reservation {
        currentUserMustBeManagerOf(reservation)
        return updateStatusAndPersist(reservation, ReservationStatus.COMPLETED)
    }

    private fun updateStatusAndPersist(reservation: Reservation, targetStatus: ReservationStatus): Reservation {
        reservation.status = targetStatus
        return reservationRepository.save(reservation)
    }

    private fun currentUserMustBeManagerOf(reservation: Reservation) {
        if (getCurrentUserBeingManagerOf(reservation.table.restaurant) != reservation.manager) {
            throw UserException("Вы не являетесь ответственным сотрудником за бронь $reservation")
        }
    }

    // TODO should it be checked on security level?
    private fun getCurrentUserBeingManagerOf(restaurant: Restaurant): Manager {
        val employee = employeeRepository.findByRestaurantAndUser(
            restaurant,
            currentUserService.currentUserEntity
        ) ?: throw UserException("Вы не являетесь сотрудником ресторана \"${restaurant.name}\".")
        if (employee !is Manager) {
            throw UserException("Вы не являетесь менеджером ресторана \"${restaurant.name}\".")
        }
        return employee
    }
}