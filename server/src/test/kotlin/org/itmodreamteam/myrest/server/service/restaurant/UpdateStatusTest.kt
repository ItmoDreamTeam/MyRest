package org.itmodreamteam.myrest.server.service.restaurant

import org.assertj.core.api.Assertions.assertThat
import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.server.service.notification.NotificationService
import org.itmodreamteam.myrest.server.view.assembler.ModelViewAssembler
import org.itmodreamteam.myrest.shared.restaurant.RestaurantStatus
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
@ContextConfiguration(classes = [UpdateStatusTest.Config::class])
class UpdateStatusTest {

    @Autowired
    lateinit var restaurantService: RestaurantService

    @Autowired
    lateinit var restaurantRepository: RestaurantRepository

    @MockBean
    lateinit var notificationService: NotificationService

    private var identifier: Long = 0

    @Before
    fun setup() {
        identifier = restaurantRepository.save(Restaurant("Pizza", "Italian food", "ИНН")).id
    }

    @Test
    fun `Given existing restaurant, when admin update status, then success update`() {
        restaurantService.updateStatus(identifier, RestaurantStatus.ACTIVE)

        val updatedRestaurant = restaurantService.getById(identifier)

        assertThat(updatedRestaurant.status).isEqualTo(RestaurantStatus.ACTIVE)
    }

    @Test(expected = UserException::class)
    fun `Given existing restaurant, when admin update status with the same value, then failure`() {
        restaurantService.updateStatus(identifier, RestaurantStatus.PENDING)
    }

    @Test(expected = UserException::class)
    fun `When updating non existing restaurant, then failure`() {
        restaurantService.updateStatus(101110, RestaurantStatus.PENDING)
    }

    @TestConfiguration
    @ComponentScan(basePackageClasses = [Config::class, ModelViewAssembler::class])
    open class Config
}
