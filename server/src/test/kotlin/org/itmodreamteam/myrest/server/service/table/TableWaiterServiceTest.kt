package org.itmodreamteam.myrest.server.service.table

import org.assertj.core.api.Assertions.assertThat
import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.model.restaurant.RestaurantTable
import org.itmodreamteam.myrest.server.model.restaurant.Waiter
import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.server.repository.restaurant.EmployeeRepository
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantTableRepository
import org.itmodreamteam.myrest.server.repository.user.UserRepository
import org.itmodreamteam.myrest.server.view.assembler.ModelViewAssembler
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
@ContextConfiguration(classes = [TableWaiterServiceTest.Config::class])
class TableWaiterServiceTest {

    @Autowired
    lateinit var tableWaiterService: TableWaiterService

    @Autowired
    lateinit var tableRepository: RestaurantTableRepository

    @Autowired
    lateinit var employeeRepository: EmployeeRepository

    @Autowired
    lateinit var restaurantRepository: RestaurantRepository

    @Autowired
    lateinit var userRepository: UserRepository

    lateinit var table: RestaurantTable
    lateinit var waiterAlice: Waiter
    lateinit var waiterBob: Waiter

    @Before
    fun setUp() {
        val restaurant = restaurantRepository.save(Restaurant("Mr Chu", "de Finibus Bonorum", "Inc"))
        table = tableRepository.save(RestaurantTable(restaurant, 100, null, 10))

        val alice = userRepository.save(User("Alice", "Wonderland"))
        val bob = userRepository.save(User("Bob", "Bobov"))
        waiterAlice = employeeRepository.save(Waiter(restaurant, alice))
        waiterBob = employeeRepository.save(Waiter(restaurant, bob))
    }

    @Test
    fun `Given newly added table, when get table's waiters, then waiters list is empty`() {
        val waiters = tableWaiterService.getTableWaiters(table.id)
        assertThat(waiters).isEmpty()
    }

    @Test
    fun `Given table and waiters, when add waiters to table, then waiters are added`() {
        tableWaiterService.addWaiter(table.id, waiterAlice.id)
        tableWaiterService.addWaiter(table.id, waiterBob.id)

        val waiters = tableWaiterService.getTableWaiters(table.id)
        assertThat(waiters.map { it.id }).containsExactlyInAnyOrder(waiterAlice.id, waiterBob.id)
    }

    @Test
    fun `Given table and waiter, when add waiter to table multiple times, then waiters list contains one waiter`() {
        tableWaiterService.addWaiter(table.id, waiterAlice.id)
        tableWaiterService.addWaiter(table.id, waiterAlice.id)
        tableWaiterService.addWaiter(table.id, waiterAlice.id)

        val waiters = tableWaiterService.getTableWaiters(table.id)
        assertThat(waiters.map { it.id }).containsExactly(waiterAlice.id)
    }

    @Test
    fun `Given two waiters added to table, when remove one waiter, then one waiter is left`() {
        tableWaiterService.addWaiter(table.id, waiterAlice.id)
        tableWaiterService.addWaiter(table.id, waiterBob.id)

        tableWaiterService.removeWaiter(table.id, waiterBob.id)

        val waiters = tableWaiterService.getTableWaiters(table.id)
        assertThat(waiters.map { it.id }).containsExactly(waiterAlice.id)
    }

    @Test(expected = UserException::class)
    fun `Given restaurant table and waiter from different restaurant, when add waiter to table, then failure`() {
        val restaurant = restaurantRepository.save(Restaurant("Do-Do", "...", "Inc"))
        val user = userRepository.save(User("Michael", "Jackson"))
        val waiter = employeeRepository.save(Waiter(restaurant, user))

        tableWaiterService.addWaiter(table.id, waiter.id)
    }

    @Test(expected = UserException::class)
    fun `Given waiter, when add waiter to non-existent table, then failure`() {
        tableWaiterService.addWaiter(-1, waiterAlice.id)
    }

    @Test(expected = UserException::class)
    fun `Given table, when add non-existent waiter to table, then failure`() {
        tableWaiterService.addWaiter(table.id, -1)
    }

    @Test(expected = UserException::class)
    fun `When get waiters of non-existent table, then failure`() {
        tableWaiterService.getTableWaiters(-1)
    }

    @Test(expected = UserException::class)
    fun `Given table and its waiter, when remove waiter from non-existent table, then failure`() {
        tableWaiterService.addWaiter(table.id, waiterAlice.id)

        tableWaiterService.removeWaiter(-1, waiterAlice.id)
    }

    @Test(expected = UserException::class)
    fun `Given table and its waiter, when remove non-existent waiter from table, then failure`() {
        tableWaiterService.addWaiter(table.id, waiterAlice.id)

        tableWaiterService.removeWaiter(table.id, -1)
    }

    @Test
    fun `Given tables and their waiters, when get waiter's tables, then return correct tables`() {
        val restaurant = restaurantRepository.save(Restaurant("AAA", "/", "Inc"))
        val tableFor3 = tableRepository.save(RestaurantTable(restaurant, 1, null, 3))
        val tableFor4 = tableRepository.save(RestaurantTable(restaurant, 2, null, 4))
        val tableFor5 = tableRepository.save(RestaurantTable(restaurant, 3, null, 5))
        val tableFor8 = tableRepository.save(RestaurantTable(restaurant, 4, null, 8))

        val john = userRepository.save(User("John", "Rain"))
        val jack = userRepository.save(User("Jack", "Nightingale"))
        val waiterJohn = employeeRepository.save(Waiter(restaurant, john))
        val waiterJack = employeeRepository.save(Waiter(restaurant, jack))

        tableWaiterService.addWaiter(tableFor3.id, waiterJohn.id)
        tableWaiterService.addWaiter(tableFor4.id, waiterJack.id)
        tableWaiterService.addWaiter(tableFor5.id, waiterJohn.id)
        tableWaiterService.addWaiter(tableFor8.id, waiterJohn.id)
        tableWaiterService.addWaiter(tableFor8.id, waiterJack.id)

        val johnTables = tableWaiterService.getWaiterTables(waiterJohn.id)
        assertThat(johnTables.map { it.id }).containsExactlyInAnyOrder(tableFor3.id, tableFor5.id, tableFor8.id)

        val jackTables = tableWaiterService.getWaiterTables(waiterJack.id)
        assertThat(jackTables.map { it.id }).containsExactlyInAnyOrder(tableFor8.id, tableFor4.id)
    }

    @Test(expected = UserException::class)
    fun `When get tables of non-existent waiter, then failure`() {
        tableWaiterService.getWaiterTables(-1)
    }

    @TestConfiguration
    @ComponentScan(basePackageClasses = [Config::class, ModelViewAssembler::class])
    open class Config
}
