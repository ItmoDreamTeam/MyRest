package org.itmodreamteam.myrest.server.service.table

import org.assertj.core.api.Assertions.assertThat
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.model.restaurant.RestaurantTable
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantTableRepository
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
@ContextConfiguration(classes = [TableWaiterServiceTest.Config::class])
class TableWaiterServiceTest {

    @MockBean
    lateinit var tableWaiterService: TableWaiterService

    @Autowired
    lateinit var tableRepository: RestaurantTableRepository

    @Autowired
    lateinit var restaurantRepository: RestaurantRepository

    lateinit var table: RestaurantTable

    @Before
    fun setUp() {
        val restaurant = restaurantRepository.save(Restaurant("Mr Chu", "de Finibus Bonorum", "Inc"))
        table = tableRepository.save(RestaurantTable(restaurant, "Round Table", null, 10))
    }

    @Test
    fun `Given newly added table, when get table's waiters, then waiters list is empty`() {
        val waiters = tableWaiterService.getTableWaiters(table.id)
        assertThat(waiters).isEmpty()
    }

    @TestConfiguration
    @ComponentScan
    open class Config
}
