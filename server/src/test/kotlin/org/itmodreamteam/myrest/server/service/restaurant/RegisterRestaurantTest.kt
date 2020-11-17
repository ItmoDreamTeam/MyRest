package org.itmodreamteam.myrest.server.service.restaurant

import org.assertj.core.api.Assertions.assertThat
import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.server.service.user.SignInTest
import org.itmodreamteam.myrest.shared.restaurant.RegisterRestaurant
import org.itmodreamteam.myrest.shared.restaurant.RestaurantStatus
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import javax.validation.ConstraintViolationException

@RunWith(SpringRunner::class)
@DataJpaTest
@ContextConfiguration(classes = [RegisterRestaurantTest.Config::class])
class RegisterRestaurantTest {

    @Autowired
    lateinit var restaurantService: RestaurantService

    @Autowired
    lateinit var restaurantRepository: RestaurantRepository

    lateinit var restaurant: Restaurant

    @Before
    fun setup() {
        restaurant = restaurantRepository.save(Restaurant("Pizza", "Italian food", "ИНН"))
    }

    @Test(expected = UserException::class)
    fun `Given existing restaurant, when created restaurant with existing name, then failure`() {
        restaurantService.registerRestaurant(RegisterRestaurant("Pizza", "", ""))
    }

    @Test
    fun `When created new restaurant with valid required arguments, then new restaurant created`() {
        restaurantService.registerRestaurant(RegisterRestaurant("Pasta", "Italian food", "INN"))

        var restaurants = restaurantRepository.findAll()
        assertThat(restaurants).hasSize(2)

        var newRestaurant = restaurants[1]
        assertThat(newRestaurant.name).isEqualTo("Pasta")
        assertThat(newRestaurant.description).isEqualTo("Italian food")
        assertThat(newRestaurant.legalInfo).isEqualTo("INN")
        assertThat(newRestaurant.status).isEqualTo(RestaurantStatus.PENDING)
    }

    @Test
    fun `When created new restaurant with the whole arguments, then new restaurant created`() {
        var createdRestaurant = RegisterRestaurant(
            "Pasta",
            "Italian food",
            "docs",
            "pizzapasta.com",
            "8928335050",
            "pizza@mail.ru"
        )
        restaurantService.registerRestaurant(createdRestaurant)

        var restaurants = restaurantRepository.findAll()
        assertThat(restaurants).hasSize(2)

        var newRestaurant = restaurants[1]
        assertThat(newRestaurant.name).isEqualTo("Pasta")
        assertThat(newRestaurant.description).isEqualTo("Italian food")
        assertThat(newRestaurant.legalInfo).isEqualTo("docs")
        assertThat(newRestaurant.status).isEqualTo(RestaurantStatus.PENDING)
    }

    @Test(expected = ConstraintViolationException::class)
    fun `When created new restaurant with empty name, then failure`() {
        restaurantService.registerRestaurant(RegisterRestaurant("", "Some food", "docs"))
    }

    @Test(expected = ConstraintViolationException::class)
    fun `When created new restaurant with empty description, then failure`() {
        restaurantService.registerRestaurant(RegisterRestaurant("Pasta", "", "docs"))
    }

    @TestConfiguration
    @ComponentScan
    open class Config
}