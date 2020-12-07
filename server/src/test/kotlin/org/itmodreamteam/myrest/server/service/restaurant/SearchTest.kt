package org.itmodreamteam.myrest.server.service.restaurant

import org.assertj.core.api.Assertions.assertThat
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.server.service.notification.NotificationService
import org.itmodreamteam.myrest.shared.restaurant.RestaurantStatus
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
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

    @MockBean
    lateinit var notificationService: NotificationService

    @MockBean
    lateinit var reservationService: ReservationService

    private var restaurants = ArrayList<Restaurant>()

    @Before
    fun setup() {
        restaurants.add(Restaurant("Pizza", "Italian food", "ИНН"))
        restaurants.add(Restaurant("Pizza&Pasta", "Italian food so on", "docs"))
        restaurants.add(Restaurant("Pasta&Pizza", "italian food and more", "OOO"))
        restaurants.add(Restaurant("BBQ", "Russian cuisine", "INN"))
        restaurants.add(Restaurant("BBQ Fast Food", "Georgia and Russian cuisine", "docs"))
        restaurants.forEach { restaurantRepository.save(it) }
        restaurantRepository.save(Restaurant("Picas", "Italian food", "ИНН")).status = RestaurantStatus.ACTIVE
    }

    @Test
    fun `Given saved restaurants, when search by name contains, then get matched restaurants`() {
        val foundRestaurants = restaurantService.search(
            "Pizza",
            listOf(RestaurantStatus.PENDING),
            Pageable.unpaged()
        )

        assertThat(foundRestaurants.totalElements).isEqualTo(3)
        foundRestaurants.get().forEach { assertThat(it.name).contains("Pizza") }
    }

    @Test
    fun `Given saved restaurants, when search by description contains, then get concrete restaurant`() {
        val foundRestaurant = restaurantService.search(
            "Georgia",
            listOf(RestaurantStatus.PENDING),
            Pageable.unpaged()
        )

        assertThat(foundRestaurant.totalElements).isEqualTo(1)
        assertThat(foundRestaurant.content[0].description).contains("Georgia")
    }

    @Test
    fun `Given saved restaurants, when search by description contains ignore case, then get concrete restaurant`() {
        val foundRestaurant = restaurantService.search(
            "ITALIAN",
            listOf(RestaurantStatus.PENDING),
            Pageable.unpaged()
        )

        assertThat(foundRestaurant.totalElements).isEqualTo(3)
        assertThat(foundRestaurant.content[0].description).contains("Italian")
    }

    @Test
    fun `Given saved restaurants, when search by not contained word, then get empty page`() {
        val foundRestaurants = restaurantService.search(
            "Grill",
            listOf(RestaurantStatus.PENDING),
            Pageable.unpaged()
        )

        assertThat(foundRestaurants.isEmpty)
    }

    @Test
    fun `Given saved restaurants, when search by empty string, then get all restaurants`() {
        val foundRestaurant = restaurantService.search(
            "",
            listOf(RestaurantStatus.PENDING),
            Pageable.unpaged()
        )

        assertThat(foundRestaurant.totalElements).isEqualTo(5)
    }

    @Test
    fun `Given saved restaurants, when search all restaurants by status, then get restaurants with matched status`() {
        val foundRestaurant = restaurantService.search(
            "",
            listOf(RestaurantStatus.PENDING, RestaurantStatus.ACTIVE),
            Pageable.unpaged()
        )

        assertThat(foundRestaurant.totalElements).isEqualTo(6)
    }

    @TestConfiguration
    @ComponentScan
    open class Config
}
