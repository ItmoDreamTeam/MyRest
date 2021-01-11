package org.itmodreamteam.myrest.server.security.permission

import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.repository.restaurant.ReservationRepository
import org.itmodreamteam.myrest.server.security.UserAuthentication
import org.itmodreamteam.myrest.server.service.employee.EmployeeService
import org.itmodreamteam.myrest.shared.restaurant.EmployeePosition
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ReservationPermissionEvaluator(
    private val employeeService: EmployeeService,
    private val reservationRepository: ReservationRepository,
) : DomainPermissionEvaluator {

    override val targetType: String = "Reservation"

    override fun hasPermission(authentication: UserAuthentication, targetId: Long, permission: String): Boolean {
        val reservation = reservationRepository.findByIdOrNull(targetId)
            ?: throw UserException("reservation.not-found")
        return when (permission) {
            "write-user" -> reservation.user.id == authentication.principal.id
            "write-manager" -> {
                val restaurant = reservation.table.restaurant
                val activeManagers = employeeService.getEmployeesOfRestaurant(restaurant.id)
                    .filter { it.position == EmployeePosition.MANAGER && it.active() }
                    .map { it.user }
                return authentication.principal in activeManagers
            }
            else -> false
        }
    }
}
