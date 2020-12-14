package org.itmodreamteam.myrest.server.service.reservation

import org.activiti.api.process.model.payloads.StartMessagePayload
import org.activiti.api.process.runtime.ProcessRuntime
import org.activiti.api.process.runtime.connector.Connector
import org.activiti.api.task.runtime.TaskRuntime
import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Manager
import org.itmodreamteam.myrest.server.model.restaurant.Reservation
import org.itmodreamteam.myrest.server.repository.restaurant.EmployeeRepository
import org.itmodreamteam.myrest.server.repository.restaurant.ReservationRepository
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantTableRepository
import org.itmodreamteam.myrest.server.service.notification.NotificationService
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

interface ReservationProcessManager {

    fun onReservationRequested(id: Long)

    fun onReservationApproved(id: Long)

    fun onReservationRejected(id: Long)
}

@Service
class ReservationProcessManagerImpl(
    private val processRuntime: ProcessRuntime,
    private val taskRuntime: TaskRuntime,
    private val reservationRepository: ReservationRepository,
    private val employeeRepository: EmployeeRepository,
) : ReservationProcessManager {

    override fun onReservationRequested(id: Long) {
        val key = "R$id"
        val restaurant = getReservation(id).table.restaurant
        val managersIds = employeeRepository.findByRestaurant(restaurant)
            .filterIsInstance<Manager>()
            .map { it.user.id.toString() }
        val variables = mapOf(Pair("id", id), Pair("managers", managersIds))
        processRuntime.start(StartMessagePayload("reservation-request", key, variables))
    }

    override fun onReservationApproved(id: Long) {
        onReservationProcessed(id, true)
    }

    override fun onReservationRejected(id: Long) {
        onReservationProcessed(id, false)
    }

    private fun onReservationProcessed(id: Long, approved: Boolean) {

    }

    private fun getReservation(id: Long): Reservation {
        return reservationRepository.findByIdOrNull(id)
            ?: throw UserException("Бронь не найдена")
    }
}

@Configuration
open class Connectors(
    private val notificationService: NotificationService,
    private val reservationRepository: ReservationRepository,
    private val employeeRepository: EmployeeRepository,
    private val tableRepository: RestaurantTableRepository,
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Bean
    open fun managersNotificationConnector(): Connector = Connector { connector ->
        log.info("managersNotificationConnector")
        val id = connector.inBoundVariables["id"].toString().toLong()
        val reservation = getReservation(id)
        val restaurant = reservation.table.restaurant
        val message = "Новая заявка на бронирование была создана и ожидает подтверждения"
        employeeRepository.findByRestaurant(restaurant)
            .filterIsInstance<Manager>()
            .forEach { notificationService.notify(it.user, message) }
        connector
    }

    @Bean
    open fun waiterNotificationConnector(): Connector = Connector { connector ->
        log.info("waiterNotificationConnector")
        val id = connector.inBoundVariables["id"].toString().toLong()
        val reservation = getReservation(id)
        val restaurant = reservation.table.restaurant
        val table = reservation.table
        val message = "Новая бронь столика ${table.number} подтверждена"
        tableRepository.findByRestaurantAndNumber(restaurant, table.number)
            ?.waiters
            ?.forEach { notificationService.notify(it.user, message) }
        connector
    }

    @Bean
    open fun customerNotificationConnector(): Connector = Connector { connector ->
        log.info("customerNotificationConnector")
        val id = connector.inBoundVariables["id"].toString().toLong()
        val reservation = getReservation(id)
        val message = "Ваша бронь ${reservation.id} подтверждена"
        notificationService.notify(reservation.user, message)
        connector
    }

    private fun getReservation(id: Long): Reservation {
        return reservationRepository.findByIdOrNull(id)
            ?: throw UserException("Бронь не найдена")
    }
}

@Configuration
open class ActivitiConfiguration {

    @Component
    class UserDetailsServiceImpl : UserDetailsService {
        override fun loadUserByUsername(username: String): UserDetails {
            return object : UserDetails {
                override fun getUsername(): String = username
                override fun getPassword(): String = "*****"
                override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf()
                override fun isEnabled(): Boolean = true
                override fun isAccountNonLocked(): Boolean = true
                override fun isAccountNonExpired(): Boolean = true
                override fun isCredentialsNonExpired(): Boolean = true
            }
        }
    }
}
