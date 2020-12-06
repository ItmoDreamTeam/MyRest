package org.itmodreamteam.myrest.server.service.restaurant

import org.assertj.core.api.Assertions.assertThat
import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Manager
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.server.repository.restaurant.EmployeeRepository
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.server.repository.user.UserRepository
import org.itmodreamteam.myrest.server.service.notification.NotificationService
import org.itmodreamteam.myrest.shared.restaurant.EmployeeRestaurantStatus
import org.itmodreamteam.myrest.shared.restaurant.EmployeeUserStatus
import org.itmodreamteam.myrest.shared.restaurant.RestaurantRegistrationInfo
import org.itmodreamteam.myrest.shared.restaurant.RestaurantStatus
import org.itmodreamteam.myrest.shared.user.Role
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.fail
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import javax.validation.ConstraintViolationException

@RunWith(SpringRunner::class)
@DataJpaTest
@ContextConfiguration(classes = [RegisterTest.Config::class])
class RegisterTest {

    @Autowired
    lateinit var restaurantService: RestaurantService

    @Autowired
    lateinit var restaurantRepository: RestaurantRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var employeeRepository: EmployeeRepository

    @MockBean
    lateinit var notificationService: NotificationService

    private lateinit var restaurant: Restaurant
    private lateinit var user: User
    private lateinit var admin: User

    @Before
    fun setup() {
        user = userRepository.save(User("Pizza", "Lover"))
        val adminUser = User("Pavel", "Pavlov")
        adminUser.role = Role.ADMIN
        admin = userRepository.save(adminUser)
        restaurant = restaurantRepository.save(Restaurant("Pizza", "Italian food", "ИНН"))
    }

    @Test
    fun `When user registers restaurant, user becomes its active manager`() {
        val restaurantId = restaurantService.register(
            RestaurantRegistrationInfo("Cozy place", "Domestic cuisine", "docs"),
            user
        ).id

        val restaurant = restaurantRepository.getOne(restaurantId)
        val employee = employeeRepository.findByRestaurantAndUser(restaurant, user) ?: fail("no employee")

        assertThat(employee).isOfAnyClassIn(Manager::class.java)
        assertThat(employee.user).isEqualTo(user)
        assertThat(employee.restaurant.id).isEqualTo(restaurantId)
        assertThat(employee.userStatus).isEqualTo(EmployeeUserStatus.ACTIVE)
        assertThat(employee.restaurantStatus).isEqualTo(EmployeeRestaurantStatus.ACTIVE)
    }

    @Test
    fun `When user registers restaurant, restaurant's status is PENDING`() {
        restaurantService.register(
            RestaurantRegistrationInfo("Cozy place", "Domestic cuisine", "docs"),
            user
        )

        val savedRestaurant = restaurantRepository.getOne(restaurant.id)

        assertThat(savedRestaurant.status).isEqualTo(RestaurantStatus.PENDING)
    }

    @Test
    fun `When user registers restaurant, System admin is notified`() {
        val registeredRestaurant = restaurantService.register(
            RestaurantRegistrationInfo("Cozy place", "Domestic cuisine", "docs"),
            user
        )

        val text = "Ресторан с именем ${registeredRestaurant.name} был зарегистрирован и ожидает проверки"
        verify(notificationService, Mockito.times(1)).notify(admin, text)
    }

    @Test(expected = UserException::class)
    fun `Given existing restaurant, when create restaurant with existing name, then failure`() {
        restaurantService.register(
            RestaurantRegistrationInfo("Pizza", "", ""),
            user
        )
    }

    @Test
    fun `When create new restaurant with valid required arguments, then new restaurant created`() {
        val registeredRestaurant = restaurantService.register(
            RestaurantRegistrationInfo(
                "Pasta",
                "Italian food",
                "INN"
            ),
            user
        )

        val newRestaurant = restaurantRepository.getOne(registeredRestaurant.id)
        assertThat(newRestaurant.name).isEqualTo("Pasta")
        assertThat(newRestaurant.description).isEqualTo("Italian food")
        assertThat(newRestaurant.legalInfo).isEqualTo("INN")
        assertThat(newRestaurant.status).isEqualTo(RestaurantStatus.PENDING)
    }

    @Test
    fun `When create new restaurant with all arguments, then new restaurant created`() {
        val registeredRestaurant = restaurantService.register(
            RestaurantRegistrationInfo(
                "Pasta",
                "Italian food",
                "docs",
                "pizzapasta.com",
                "8928335050",
                "pizza@mail.ru"
            ),
            user
        )

        val newRestaurant = restaurantRepository.getOne(registeredRestaurant.id)
        assertThat(newRestaurant.name).isEqualTo("Pasta")
        assertThat(newRestaurant.description).isEqualTo("Italian food")
        assertThat(newRestaurant.legalInfo).isEqualTo("docs")
        assertThat(newRestaurant.status).isEqualTo(RestaurantStatus.PENDING)
    }

    @Test(expected = ConstraintViolationException::class)
    fun `When create new restaurant with empty name, then failure`() {
        restaurantService.register(RestaurantRegistrationInfo("", "Some food", "docs"), user)
    }

    @Test(expected = ConstraintViolationException::class)
    fun `When create new restaurant with empty description, then failure`() {
        restaurantService.register(RestaurantRegistrationInfo("Pasta", "", "docs"), user)
    }

    @TestConfiguration
    @ComponentScan
    open class Config
}
