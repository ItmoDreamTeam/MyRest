package org.itmodreamteam.myrest.server.service.employee

import org.assertj.core.api.Assertions.assertThat
import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.model.user.Identifier
import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.server.repository.user.IdentifierRepository
import org.itmodreamteam.myrest.server.repository.user.UserRepository
import org.itmodreamteam.myrest.shared.restaurant.EmployeeInvitation
import org.itmodreamteam.myrest.shared.restaurant.EmployeePosition
import org.itmodreamteam.myrest.shared.restaurant.EmployeeStatus
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
@ContextConfiguration(classes = [EmployeeInvitationTest.Config::class])
class EmployeeInvitationTest {

    @Autowired
    lateinit var employeeService: EmployeeService

    @Autowired
    lateinit var restaurantRepository: RestaurantRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var identifierRepository: IdentifierRepository

    lateinit var restaurant: Restaurant
    lateinit var user: User
    lateinit var userPhoneIdentifier: Identifier

    @Before
    fun setUp() {
        restaurant = restaurantRepository.save(Restaurant("Claude Monet", "French cuisine", "123"))
        user = userRepository.save(User("Julia", "Romanova"))
        userPhoneIdentifier = identifierRepository.save(Identifier("+79889875634", Identifier.Type.PHONE, user))
    }

    @Test
    fun `Given restaurant and user, when invite user to restaurant as waiter, then return correct employee info`() {
        val invitation = EmployeeInvitation("+79889875634", EmployeePosition.WAITER)
        val employeeInfo = employeeService.inviteEmployee(restaurant.id, invitation)

        assertThat(employeeInfo.restaurantId).isEqualTo(restaurant.id)
        assertThat(employeeInfo.userId).isEqualTo(user.id)
        assertThat(employeeInfo.position).isEqualTo(EmployeePosition.WAITER)
        assertThat(employeeInfo.status).isEqualTo(EmployeeStatus.PENDING)
    }

    @Test
    fun `Given restaurant and user, when invite user to restaurant as manager, then return correct employee info`() {
        val invitation = EmployeeInvitation("+79889875634", EmployeePosition.MANAGER)
        val employeeInfo = employeeService.inviteEmployee(restaurant.id, invitation)

        assertThat(employeeInfo.position).isEqualTo(EmployeePosition.MANAGER)
    }

    @Test(expected = UserException::class)
    fun `When invite user with non-existent phone number, then failure`() {
        val invitation = EmployeeInvitation("+79880000000", EmployeePosition.WAITER)
        employeeService.inviteEmployee(restaurant.id, invitation)
    }

    @Test(expected = UserException::class)
    fun `When invite user to non-existent restaurant, then failure`() {
        val invitation = EmployeeInvitation("+79889875634", EmployeePosition.WAITER)
        employeeService.inviteEmployee(89710, invitation)
    }

    @Test(expected = UserException::class)
    fun `Given invited user, when invite this user again, then failure`() {
        val invitation = EmployeeInvitation("+79889875634", EmployeePosition.WAITER)
        employeeService.inviteEmployee(restaurant.id, invitation)

        employeeService.inviteEmployee(restaurant.id, invitation)
    }

    @Test
    fun `Given invited employee, when get employee by ID, then return correct employee info`() {
        val invitation = EmployeeInvitation("+79889875634", EmployeePosition.MANAGER)
        val employeeInfo = employeeService.inviteEmployee(restaurant.id, invitation)

        val employeeInfoById = employeeService.getById(employeeInfo.id)
        assertThat(employeeInfoById).isEqualTo(employeeInfo)
    }

    @Test(expected = UserException::class)
    fun `When get employee by non-existent ID, then failure`() {
        employeeService.getById(5840);
    }

    @TestConfiguration
    @ComponentScan
    open class Config
}
