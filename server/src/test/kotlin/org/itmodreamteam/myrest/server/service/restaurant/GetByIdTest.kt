package org.itmodreamteam.myrest.server.service.restaurant

import org.assertj.core.api.Assertions.assertThat
import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
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
@ContextConfiguration(classes = [GetByIdTest.Config::class])
class GetByIdTest {

    @Autowired
    lateinit var restaurantService: RestaurantService

    @Autowired
    lateinit var restaurantRepository: RestaurantRepository

    @MockBean
    lateinit var notificationService: NotificationService

    @MockBean
    lateinit var reservationService: ReservationService

    private var identifier: Long = 0

    @Before
    fun setup() {
        identifier = restaurantRepository.save(Restaurant("Pizza", "Italian food", "ИНН")).id
    }

    @Test
    fun `Given existing identifier, when getById restaurant, then return existing restaurant`() {
        val restaurant = restaurantService.getById(identifier)

        assertThat(restaurant.id).isEqualTo(identifier)
        assertThat(restaurant.name).isEqualTo("Pizza")
        assertThat(restaurant.description).isEqualTo("Italian food")
        assertThat(restaurant.legalInfo).isEqualTo("ИНН")
    }

    @Test(expected = UserException::class)
    fun `When getById with not existing identifier, then failure`() {
        restaurantService.getById(191912)
    }

    @TestConfiguration
    @ComponentScan
    open class Config
}
