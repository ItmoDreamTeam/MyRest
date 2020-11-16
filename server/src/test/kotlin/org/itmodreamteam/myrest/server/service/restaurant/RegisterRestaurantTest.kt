package org.itmodreamteam.myrest.server.service.restaurant

import org.assertj.core.api.Assertions.assertThat
import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.server.service.user.SignInTest
import org.itmodreamteam.myrest.shared.restaurant.RestaurantInfo
import org.itmodreamteam.myrest.shared.restaurant.RestaurantStatus
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataJpaTest
@ContextConfiguration(classes = [SignInTest.Config::class])
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
    fun `Given existing restaurant, when created, then failure`() {
        restaurantService.registerRestaurant(RestaurantInfo("Pizza", "", ""))
    }

    @Test
    fun `Given new restaurant, when created, then success saving`() {
        restaurantService.registerRestaurant(RestaurantInfo("Pasta", "Italian food", "INN"))

        var restaurants = restaurantRepository.findAll()
        assertThat(restaurants).hasSize(2)

        var newRestaurant = restaurants[1]
        assertThat(newRestaurant.name).isEqualTo("Pasta")
        assertThat(newRestaurant.description).isEqualTo("Italian food")
        assertThat(newRestaurant.legalInfo).isEqualTo("INN")
        assertThat(newRestaurant.status).isEqualTo(RestaurantStatus.PENDING)
    }
}