package org.itmodreamteam.myrest.server.service.employee

import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Employee
import org.itmodreamteam.myrest.server.model.restaurant.Manager
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.model.restaurant.Waiter
import org.itmodreamteam.myrest.server.repository.restaurant.EmployeeRepository
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.server.repository.user.IdentifierRepository
import org.itmodreamteam.myrest.server.repository.user.UserRepository
import org.itmodreamteam.myrest.server.service.notification.NotificationService
import org.itmodreamteam.myrest.server.view.assembler.ModelViewAssembler
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
    private val employeeToEmployeeInfoAssembler: ModelViewAssembler<Employee, EmployeeInfo>
) : EmployeeService {

    override fun inviteEmployee(restaurantId: Long, invitation: EmployeeInvitation): EmployeeInfo {
        val restaurant = getRestaurantEntity(restaurantId)
        val user = identifierRepository.findByValue(invitation.phone)?.user
            ?: throw UserException("user.phone.not-found")
        val exists = employeeRepository.findByRestaurantAndUser(restaurant, user) != null
        if (exists) {
            throw UserException("employee.exists")
        }
        val employee = employeeRepository.save(
            when (invitation.position) {
                EmployeePosition.MANAGER -> Manager(restaurant, user)
                EmployeePosition.WAITER -> Waiter(restaurant, user)
            }
        )
        notificationService.notify(user, "${restaurant.name} хочет добавить вас в качестве сотрудника")
        return toEmployeeInfo(employee)
    }

    override fun updateEmployee(id: Long, userStatus: EmployeeUserStatus): EmployeeInfo {
        val employee = getEmployeeEntity(id)
        employee.userStatus = userStatus
        return toEmployeeInfo(employee)
    }

    override fun updateEmployee(id: Long, restaurantStatus: EmployeeRestaurantStatus): EmployeeInfo {
        val employee = getEmployeeEntity(id)
        employee.restaurantStatus = restaurantStatus
        return toEmployeeInfo(employee)
    }

    override fun getRestaurantsOfUser(userId: Long): List<EmployeeInfo> {
        val user = userRepository.findByIdOrNull(userId)
            ?: throw UserException("user.not-found")
        return employeeRepository.findByUser(user).map { toEmployeeInfo(it) }
    }

    override fun getEmployeesOfRestaurant(restaurantId: Long): List<EmployeeInfo> {
        val restaurant = getRestaurantEntity(restaurantId)
        return employeeRepository.findByRestaurant(restaurant).map { toEmployeeInfo(it) }
    }

    override fun getById(id: Long): EmployeeInfo {
        val employee = getEmployeeEntity(id)
        return toEmployeeInfo(employee)
    }

    private fun toEmployeeInfo(employee: Employee): EmployeeInfo {
        return employeeToEmployeeInfoAssembler.toView(employee)
    }

    private fun getRestaurantEntity(id: Long): Restaurant {
        return restaurantRepository.findByIdOrNull(id)
            ?: throw UserException("restaurant.not-found")
    }

    private fun getEmployeeEntity(id: Long): Employee {
        return employeeRepository.findByIdOrNull(id)
            ?: throw UserException("employee.not-found")
    }
}
