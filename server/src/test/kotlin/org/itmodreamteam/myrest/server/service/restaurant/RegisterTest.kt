package org.itmodreamteam.myrest.server.service.restaurant

import org.assertj.core.api.Assertions.assertThat
import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.shared.restaurant.RestaurantRegistrationInfo
import org.itmodreamteam.myrest.shared.restaurant.RestaurantStatus
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.repository.findByIdOrNull
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

    private lateinit var restaurant: Restaurant

    @Before
    fun setup() {
        restaurant = restaurantRepository.save(Restaurant("Pizza", "Italian food", "ИНН"))
    }

    @Test(expected = UserException::class)
    fun `Given existing restaurant, when create restaurant with existing name, then failure`() {
        restaurantService.register(RestaurantRegistrationInfo("Pizza", "", ""))
    }

    @Test
    fun `When create new restaurant with valid required arguments, then new restaurant created`() {
        val registeredRestaurant = restaurantService.register(
            RestaurantRegistrationInfo(
                "Pasta",
                "Italian food",
                "INN"
            )
        )

        val newRestaurant = restaurantRepository.findByIdOrNull(registeredRestaurant.id)

        assertThat(newRestaurant).isNotNull
        assertThat(newRestaurant?.name).isEqualTo("Pasta")
        assertThat(newRestaurant?.description).isEqualTo("Italian food")
        assertThat(newRestaurant?.legalInfo).isEqualTo("INN")
        assertThat(newRestaurant?.status).isEqualTo(RestaurantStatus.PENDING)
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
            )
        )

        val newRestaurant = restaurantRepository.findByIdOrNull(registeredRestaurant.id)
        assertThat(newRestaurant).isNotNull
        assertThat(newRestaurant?.name).isEqualTo("Pasta")
        assertThat(newRestaurant?.description).isEqualTo("Italian food")
        assertThat(newRestaurant?.legalInfo).isEqualTo("docs")
        assertThat(newRestaurant?.status).isEqualTo(RestaurantStatus.PENDING)
    }

    @Test(expected = ConstraintViolationException::class)
    fun `When create new restaurant with empty name, then failure`() {
        restaurantService.register(RestaurantRegistrationInfo("", "Some food", "docs"))
    }

    @Test(expected = ConstraintViolationException::class)
    fun `When create new restaurant with empty description, then failure`() {
        restaurantService.register(RestaurantRegistrationInfo("Pasta", "", "docs"))
    }

    @TestConfiguration
    @ComponentScan
    open class Config
}
