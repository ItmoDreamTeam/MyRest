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
import org.springframework.data.domain.Pageable
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DataJpaTest
@ContextConfiguration(classes = [SearchTest.Config::class])
class SearchTest {

    @Autowired
    lateinit var restaurantService: RestaurantService

    @Autowired
    lateinit var restaurantRepository: RestaurantRepository

    private var restaurants: ArrayList<Restaurant> = ArrayList()

    @Before
    fun setup() {
        restaurants.add(Restaurant("Pizza", "Italian food", "ИНН"))
        restaurants.add(Restaurant("Pizza&Pasta", "Italian food", "docs"))
        restaurants.add(Restaurant("Pasta&Pizza", "italian food and more", "OOO"))
        restaurants.add(Restaurant("BBQ", "Russian cuisine", "INN"))
        restaurants.add(Restaurant("BBQ Fast Food", "Georgia and Russian cuisine", "docs"))
        restaurants.forEach{ restaurantRepository.save(it) }
    }

    @Test
    fun `Given saved restaurants, when search by existing name like, then get matched restaurants`() {
        val foundRestaurants = restaurantService.search("Pizza", Pageable.unpaged())

        assertThat(foundRestaurants.totalElements).isEqualTo(3)
        foundRestaurants.get().forEach { assertThat(it.name).contains("Pizza") }
    }

    @Test
    fun `Given saved restaurants, when search one existing restaurant, then get concrete restaurant`() {
        val foundRestaurant = restaurantService.search("Georgia", Pageable.unpaged())

        assertThat(foundRestaurant.totalElements).isEqualTo(1)
        assertThat(foundRestaurant.content[0].description).contains("Georgia")
    }

    @Test
    fun `Given saved restaurants, when search existing restaurant ignore case, then get concrete restaurant`() {
        val foundRestaurant = restaurantService.search("gEorgia", Pageable.unpaged())

        assertThat(foundRestaurant.totalElements).isEqualTo(1)
        assertThat(foundRestaurant.content[0].description).contains("Georgia")
    }

    @Test
    fun `Given saved restaurants, when search by not existing name like, then get empty page`() {
        val foundRestaurants = restaurantService.search("Grill", Pageable.unpaged())

        assertThat(foundRestaurants.totalElements).isEqualTo(0)
    }

    @Test
    fun `Given saved restaurants, when search by existing description like, then get matched restaurants`() {
        val foundRestaurants = restaurantService.search("Russian", Pageable.unpaged())

        assertThat(foundRestaurants.totalElements).isEqualTo(2)
        foundRestaurants.get().forEach { assertThat(it.description).contains("Russian") }
    }

    @Test
    fun `Given saved restaurants, when search by not existing description like, then get empty page`() {
        val foundRestaurants = restaurantService.search("Grill", Pageable.unpaged())

        assertThat(foundRestaurants.totalElements).isEqualTo(0)
    }

    @Test
    fun `Given saved restaurants, when search by empty string, then get all restaurants`() {
        val foundRestaurant = restaurantService.search("", Pageable.unpaged())

        assertThat(foundRestaurant.totalElements).isEqualTo(5)
    }

    @TestConfiguration
    @ComponentScan
    open class Config
}