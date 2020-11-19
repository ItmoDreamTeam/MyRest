package org.itmodreamteam.myrest.server.service.employee

import org.assertj.core.api.Assertions.assertThat
import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Employee
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.model.restaurant.Waiter
import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.server.repository.restaurant.EmployeeRepository
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.server.repository.user.UserRepository
import org.itmodreamteam.myrest.shared.restaurant.EmployeeRestaurantStatus
import org.itmodreamteam.myrest.shared.restaurant.EmployeeUserStatus
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataJpaTest
@ContextConfiguration(classes = [EmployeeStatusUpdateTest.Config::class])
class EmployeeStatusUpdateTest {

    @Autowired
    lateinit var employeeService: EmployeeService

    @Autowired
    lateinit var employeeRepository: EmployeeRepository

    @Autowired
    lateinit var restaurantRepository: RestaurantRepository

    @Autowired
    lateinit var userRepository: UserRepository

    lateinit var employee: Employee

    @Before
    fun setUp() {
        val user = userRepository.save(User("Caihong", "Huang"))
        val restaurant = restaurantRepository.save(Restaurant("Red Dragon", "Chinese cuisine", "顧府入属束"))
        employee = employeeRepository.save(Waiter(restaurant, user))
    }

    @Test
    fun `Given invited waiter, when user accepts invitation, then employee is active`() {
        val employee = employeeService.updateEmployee(employee.id, EmployeeUserStatus.ACTIVE)

        assertThat(employee.userStatus).isEqualTo(EmployeeUserStatus.ACTIVE)
        assertThat(employee.restaurantStatus).isEqualTo(EmployeeRestaurantStatus.ACTIVE)
    }

    @Test
    fun `Given invited waiter, when user blocks invitation, then invitation is blocked by user`() {
        val employee = employeeService.updateEmployee(employee.id, EmployeeUserStatus.BLOCKED)

        assertThat(employee.userStatus).isEqualTo(EmployeeUserStatus.BLOCKED)
        assertThat(employee.restaurantStatus).isEqualTo(EmployeeRestaurantStatus.ACTIVE)
    }

    @Test
    fun `Given active waiter, when restaurant blocks waiter, then employee is blocked by restaurant`() {
        employeeService.updateEmployee(employee.id, EmployeeUserStatus.ACTIVE)

        val employee = employeeService.updateEmployee(employee.id, EmployeeRestaurantStatus.BLOCKED)

        assertThat(employee.userStatus).isEqualTo(EmployeeUserStatus.ACTIVE)
        assertThat(employee.restaurantStatus).isEqualTo(EmployeeRestaurantStatus.BLOCKED)
    }

    @Test
    fun `Given employee blocked invitation by mistake, when employee unblocks invitation, then employee is active`() {
        employeeService.updateEmployee(employee.id, EmployeeUserStatus.BLOCKED)

        val employee = employeeService.updateEmployee(employee.id, EmployeeUserStatus.ACTIVE)

        assertThat(employee.userStatus).isEqualTo(EmployeeUserStatus.ACTIVE)
        assertThat(employee.restaurantStatus).isEqualTo(EmployeeRestaurantStatus.ACTIVE)
    }

    @Test(expected = UserException::class)
    fun `When user updates non-existent employee, then failure`() {
        employeeService.updateEmployee(-300, EmployeeUserStatus.ACTIVE)
    }

    @Test(expected = UserException::class)
    fun `When restaurant updates non-existent employee, then failure`() {
        employeeService.updateEmployee(-300, EmployeeRestaurantStatus.BLOCKED)
    }

    @TestConfiguration
    @ComponentScan
    open class Config
}
