package org.itmodreamteam.myrest.server.service.employee

import org.assertj.core.api.Assertions.assertThat
import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.model.restaurant.Waiter
import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.server.repository.restaurant.EmployeeRepository
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.server.repository.user.UserRepository
import org.itmodreamteam.myrest.server.service.notification.NotificationService
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataJpaTest
@ContextConfiguration(classes = [EmployeesRetrievalTest.Config::class])
class EmployeesRetrievalTest {

    @Autowired
    lateinit var employeeService: EmployeeService

    @Autowired
    lateinit var employeeRepository: EmployeeRepository

    @Autowired
    lateinit var restaurantRepository: RestaurantRepository

    @Autowired
    lateinit var userRepository: UserRepository

    lateinit var italianRestaurant: Restaurant
    lateinit var frenchRestaurant: Restaurant

    lateinit var italianRestaurantEmployees: List<User>
    lateinit var frenchRestaurantEmployees: List<User>

    @Before
    fun setUp() {
        italianRestaurant = restaurantRepository.save(Restaurant("Percorso", "Italian cuisine", "123"))
        frenchRestaurant = restaurantRepository.save(Restaurant("Claude Monet", "French cuisine", "321"))

        italianRestaurantEmployees = userRepository.saveAll(listOf(User("Gianni", "Ferrari"), User("Enzo", "Ricci")))
        frenchRestaurantEmployees = userRepository.saveAll(
            listOf(
                User("Charlotte", "Badeaux"),
                User("Louis", "Chevrolet"),
                User("Nicolas", "Laflamme"),
            )
        )

        employeeRepository.saveAll(italianRestaurantEmployees.map { Waiter(italianRestaurant, it) })
        employeeRepository.saveAll(frenchRestaurantEmployees.map { Waiter(frenchRestaurant, it) })
    }

    @Test
    fun `When get restaurants of Italian restaurant employee, then return Italian restaurant`() {
        val italianRestaurantEmployeeId = italianRestaurantEmployees.first().id
        val restaurants = employeeService.getRestaurantsOfUser(italianRestaurantEmployeeId)

        assertThat(restaurants).hasSize(1)
        assertThat(restaurants.first().user.id).isEqualTo(italianRestaurantEmployeeId)
        assertThat(restaurants.first().restaurant.id).isEqualTo(italianRestaurant.id)
    }

    @Test
    fun `When get employees of French restaurant, then return all French restaurant employees`() {
        val employees = employeeService.getEmployeesOfRestaurant(frenchRestaurant.id)

        assertThat(employees).allMatch { it.restaurant.id == frenchRestaurant.id }
        assertThat(employees.map { it.user.id }.toSet()).isEqualTo(frenchRestaurantEmployees.map { it.id }.toSet())
    }

    @Test(expected = UserException::class)
    fun `When get restaurants of non-existent user, then failure`() {
        employeeService.getRestaurantsOfUser(-100)
    }

    @Test(expected = UserException::class)
    fun `When get employees of non-existent restaurant, then failure`() {
        employeeService.getEmployeesOfRestaurant(-200)
    }

    @TestConfiguration
    @ComponentScan(
        "org.itmodreamteam.myrest.server.service.employee",
        "org.itmodreamteam.myrest.server.service.restaurant",
        "org.itmodreamteam.myrest.server.service.user",
    )
    @MockBean(NotificationService::class)
    open class Config
}
