package org.itmodreamteam.myrest.server.service.restaurant

import org.assertj.core.api.Assertions.assertThat
import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.server.service.notification.NotificationService
import org.itmodreamteam.myrest.shared.restaurant.UpdateRestaurant
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
@ContextConfiguration(classes = [UpdateRestaurantTest.Config::class])
class UpdateRestaurantTest {

    @Autowired
    lateinit var restaurantService: RestaurantService

    @Autowired
    lateinit var restaurantRepository: RestaurantRepository

    @MockBean
    lateinit var notificationService: NotificationService

    lateinit var restaurant: Restaurant

    @Before
    fun setup() {
        restaurant = restaurantRepository.save(Restaurant("Pizza", "Italian food", "ИНН"))
    }

    @Test
    fun `Given existing restaurant, when updating it with valid arguments, then success updating`() {
        val updatedRestaurant = UpdateRestaurant(
            "Pizza",
            "Italian and european food",
            "license",
            "pizza.com",
            "8928335050",
            "pizzana@mail.ru"
        )
        restaurantService.updateRestaurant(updatedRestaurant)

        var restaurants = restaurantRepository.findAll()
        assertThat(restaurants).hasSize(1)

        var existingRestaurant = restaurants[0]
        assertThat(existingRestaurant.name).isEqualTo("Pizza")
        assertThat(existingRestaurant.description).isEqualTo("Italian and european food")
        assertThat(existingRestaurant.legalInfo).isEqualTo("license")
        assertThat(existingRestaurant.websiteUrl).isEqualTo("pizza.com")
        assertThat(existingRestaurant.phone).isEqualTo("8928335050")
    }

    @Test(expected = UserException::class)
    fun `When updating restaurant with empty name, then failure`() {
        restaurantService.updateRestaurant(UpdateRestaurant(""))
    }

    @Test(expected = UserException::class)
    fun `When updating not existing restaurant, then failure`() {
        restaurantService.updateRestaurant(UpdateRestaurant("Shaverma"))
    }

    @Test(expected = UserException::class)
    fun `Given existing restaurant, when updating with empty arguments, then failure`() {
        val updatedRestaurant = UpdateRestaurant(
            "Pizza",
            "",
            "",
        )
        restaurantService.updateRestaurant(updatedRestaurant)
    }

    @Test(expected = UserException::class)
    fun `When updating with null arguments, then failure`() {
        restaurantService.updateRestaurant(UpdateRestaurant("Pizza"))
    }

    @TestConfiguration
    @ComponentScan
    open class Config
}