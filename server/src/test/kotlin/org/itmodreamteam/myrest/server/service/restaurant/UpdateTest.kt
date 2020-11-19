package org.itmodreamteam.myrest.server.service.restaurant

import org.assertj.core.api.Assertions.assertThat
import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.shared.restaurant.RestaurantUpdateInfo
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
@ContextConfiguration(classes = [UpdateTest.Config::class])
class UpdateTest {

    @Autowired
    lateinit var restaurantService: RestaurantService

    @Autowired
    lateinit var restaurantRepository: RestaurantRepository

    private lateinit var restaurant: Restaurant
    private var identifier: Long = 0

    @Before
    fun setup() {
        restaurant = restaurantRepository.save(Restaurant("Pizza", "Italian food", "ИНН"))
        identifier = restaurant.id
    }

    @Test
    fun `Given existing restaurant, when updating it with valid arguments, then success updating`() {
        val updatedRestaurant = RestaurantUpdateInfo(
            identifier,
            "Pizza",
            "Italian and european food",
            "license",
            "pizza.com",
            "8928335050",
            "pizzana@mail.ru"
        )
        restaurantService.update(updatedRestaurant)

        val restaurants = restaurantRepository.findAll()
        assertThat(restaurants).hasSize(1)

        val existingRestaurant = restaurants[0]
        assertThat(existingRestaurant.name).isEqualTo("Pizza")
        assertThat(existingRestaurant.description).isEqualTo("Italian and european food")
        assertThat(existingRestaurant.legalInfo).isEqualTo("license")
        assertThat(existingRestaurant.websiteUrl).isEqualTo("pizza.com")
        assertThat(existingRestaurant.phone).isEqualTo("8928335050")
    }

    @Test(expected = UserException::class)
    fun `When updating not existing restaurant, then failure`() {
        restaurantService.update(RestaurantUpdateInfo(1001110))
    }

    @Test(expected = UserException::class)
    fun `Given existing restaurant, when updating with empty arguments, then failure`() {
        val updatedRestaurant = RestaurantUpdateInfo(
            identifier,
            "",
            "",
            "",
            "",
            "",
            ""
        )
        restaurantService.update(updatedRestaurant)
    }

    @Test(expected = UserException::class)
    fun `When updating with null arguments, then failure`() {
        restaurantService.update(RestaurantUpdateInfo(identifier))
    }

    @TestConfiguration
    @ComponentScan
    open class Config
}