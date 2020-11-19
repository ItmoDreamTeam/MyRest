package org.itmodreamteam.myrest.server.service.employee

import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Employee
import org.itmodreamteam.myrest.server.model.restaurant.Manager
import org.itmodreamteam.myrest.server.model.restaurant.Waiter
import org.itmodreamteam.myrest.server.repository.restaurant.EmployeeRepository
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.server.repository.user.IdentifierRepository
import org.itmodreamteam.myrest.server.repository.user.UserRepository
import org.itmodreamteam.myrest.server.service.notification.NotificationService
import org.itmodreamteam.myrest.shared.restaurant.*
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class EmployeeServiceImpl(
    private val employeeRepository: EmployeeRepository,
    private val restaurantRepository: RestaurantRepository,
    private val userRepository: UserRepository,
    private val identifierRepository: IdentifierRepository,
    private val notificationService: NotificationService,
) : EmployeeService {

    override fun inviteEmployee(restaurantId: Long, invitation: EmployeeInvitation): EmployeeInfo {
        val restaurant = restaurantRepository.findByIdOrNull(restaurantId)
            ?: throw UserException("Ресторан не найден")
        val user = identifierRepository.findByValue(invitation.phone)?.user
            ?: throw UserException("Пользователь не найден")
        val exists = employeeRepository.findByRestaurantAndUser(restaurant, user) != null
        if (exists) {
            throw UserException("Сотрудник уже существует")
        }
        val employee = employeeRepository.save(
            when (invitation.position) {
                EmployeePosition.MANAGER -> Manager(restaurant, user)
                EmployeePosition.WAITER -> Waiter(restaurant, user)
            }
        )
        notificationService.notify(user, "${restaurant.name} хочет добавить вас в качестве сотрудника")
        return getById(employee.id)
    }

    override fun updateEmployee(id: Long, userStatus: EmployeeUserStatus): EmployeeInfo {
        TODO("Not yet implemented")
    }

    override fun updateEmployee(id: Long, restaurantStatus: EmployeeRestaurantStatus): EmployeeInfo {
        TODO("Not yet implemented")
    }

    override fun getRestaurantsOfUser(userId: Long): List<EmployeeInfo> {
        val user = userRepository.findByIdOrNull(userId)
            ?: throw UserException("Пользователь не найден")
        return employeeRepository.findByUser(user).map { toEmployeeInfo(it) }
    }

    override fun getEmployeesOfRestaurant(restaurantId: Long): List<EmployeeInfo> {
        val restaurant = restaurantRepository.findByIdOrNull(restaurantId)
            ?: throw UserException("Ресторан не найден")
        return employeeRepository.findByRestaurant(restaurant).map { toEmployeeInfo(it) }
    }

    override fun getById(id: Long): EmployeeInfo {
        val employee = employeeRepository.findByIdOrNull(id)
            ?: throw UserException("Сотрудник не найден")
        return toEmployeeInfo(employee)
    }

    override fun toEmployeeInfo(employee: Employee): EmployeeInfo {
        val position = when (employee) {
            is Manager -> EmployeePosition.MANAGER
            is Waiter -> EmployeePosition.WAITER
            else -> throw IllegalStateException()
        }
        return EmployeeInfo(
            employee.id,
            employee.restaurant.id,
            employee.user.id,
            position,
            employee.userStatus,
            employee.restaurantStatus
        )
    }
}
