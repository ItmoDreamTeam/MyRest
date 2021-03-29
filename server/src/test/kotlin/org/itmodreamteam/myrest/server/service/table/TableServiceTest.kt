package org.itmodreamteam.myrest.server.service.table

import org.assertj.core.api.Assertions.assertThat
import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.server.view.assembler.ModelViewAssembler
import org.itmodreamteam.myrest.shared.table.TableInfo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import javax.persistence.EntityManager
import javax.validation.ConstraintViolationException

@RunWith(SpringRunner::class)
@DataJpaTest
@ContextConfiguration(classes = [TableServiceTest.Config::class])
class TableServiceTest {

    @Autowired
    lateinit var tableService: TableService

    @Autowired
    lateinit var restaurantRepository: RestaurantRepository

    @Autowired
    lateinit var entityManager: EntityManager

    lateinit var restaurant: Restaurant

    @Before
    fun setUp() {
        restaurant = restaurantRepository.save(Restaurant("Vilka", "Stolovaya", "☕"))
    }

    @Test
    fun `Given restaurant, when add tables, then tables are added`() {
        val table1 = TableInfo(1, "By the window", 4)
        val table2 = TableInfo(2, null, 8)
        tableService.addTable(restaurant.id, table1)
        tableService.addTable(restaurant.id, table2)

        val tables = tableService.getRestaurantTables(restaurant.id)
        assertThat(tables).hasSize(2)
        assertThat(tables.map { it.restaurant }).allMatch { it.id == restaurant.id }
        assertThat(tables.map { it.info }.toSet()).isEqualTo(setOf(table1, table2))
    }

    @Test
    fun `Given restaurant table, when update table info, then info is updated`() {
        val initialInfo = TableInfo(71, null, 3)
        val initialTable = tableService.addTable(restaurant.id, initialInfo)

        val newInfo = TableInfo(72, "Some description", 4)
        val updatedTable = tableService.updateTable(initialTable.id, newInfo)

        assertThat(updatedTable.id).isEqualTo(initialTable.id)
        assertThat(updatedTable.restaurant).isEqualTo(initialTable.restaurant)
        assertThat(updatedTable.info.number).isEqualTo(72)
        assertThat(updatedTable.info.description).isEqualTo("Some description")
        assertThat(updatedTable.info.numberOfSeats).isEqualTo(4)
    }

    @Test
    fun `Given restaurant table, when get table info, then info is correct`() {
        val info = TableInfo(71, "The largest table", 18)
        val table = tableService.addTable(restaurant.id, info)

        val retrievedTable = tableService.getTable(table.id)

        assertThat(retrievedTable.id).isEqualTo(table.id)
        assertThat(retrievedTable.restaurant.id).isEqualTo(restaurant.id)
        assertThat(retrievedTable.info.number).isEqualTo(71)
        assertThat(retrievedTable.info.description).isEqualTo("The largest table")
        assertThat(retrievedTable.info.numberOfSeats).isEqualTo(18)
    }

    @Test
    fun `Given restaurant table, when remove table, then restaurant has no tables`() {
        val info = TableInfo(71, "The largest table", 18)
        val table = tableService.addTable(restaurant.id, info)

        tableService.removeTable(table.id)

        val tables = tableService.getRestaurantTables(restaurant.id)
        assertThat(tables).isEmpty()
    }

    @Test(expected = UserException::class)
    fun `When add table for non-existent restaurant, then failure`() {
        tableService.addTable(-1, TableInfo(100, null, 2))
    }

    @Test(expected = UserException::class)
    fun `Given restaurant table, when add table with existing number, then failure`() {
        tableService.addTable(restaurant.id, TableInfo(100, null, 2))

        tableService.addTable(restaurant.id, TableInfo(100, null, 2))
    }

    @Test(expected = ConstraintViolationException::class)
    fun `When add table with negative number, then failure`() {
        tableService.addTable(restaurant.id, TableInfo(-3, null, 2))
    }

    @Test(expected = ConstraintViolationException::class)
    fun `When add table with zero seats, then failure`() {
        tableService.addTable(restaurant.id, TableInfo(100, null, 0))
    }

    @Test(expected = ConstraintViolationException::class)
    fun `When add table with negative number of seats, then failure`() {
        tableService.addTable(restaurant.id, TableInfo(100, null, -3))
    }

    @Test(expected = UserException::class)
    fun `When update non-existent table, then failure`() {
        tableService.updateTable(-4, TableInfo(100, null, 2))
    }

    @Test(expected = UserException::class)
    fun `Given two restaurant tables, when set one table number same as another table number, then failure`() {
        val table1 = tableService.addTable(restaurant.id, TableInfo(71, null, 5))
        val table2 = tableService.addTable(restaurant.id, TableInfo(72, null, 5))

        tableService.updateTable(table1.id, TableInfo(table2.info.number, null, 2))
    }

    @Test(expected = ConstraintViolationException::class)
    fun `Given restaurant table, when set table number to negative value, then failure`() {
        val table = tableService.addTable(restaurant.id, TableInfo(100, null, 5))

        tableService.updateTable(table.id, TableInfo(-3, null, 2))
        entityManager.flush()
    }

    @Test(expected = ConstraintViolationException::class)
    fun `Given restaurant table, when set number of table seats to zero, then failure`() {
        val table = tableService.addTable(restaurant.id, TableInfo(100, null, 5))

        tableService.updateTable(table.id, TableInfo(100, null, 0))
        entityManager.flush()
    }

    @Test(expected = ConstraintViolationException::class)
    fun `Given restaurant table, when set negative number of table seats, then failure`() {
        val table = tableService.addTable(restaurant.id, TableInfo(100, null, 5))

        tableService.updateTable(table.id, TableInfo(100, null, -3))
        entityManager.flush()
    }

    @Test(expected = UserException::class)
    fun `When get non-existent table, then failure`() {
        tableService.getTable(-7)
    }

    @Test(expected = UserException::class)
    fun `When remove non-existent table, then failure`() {
        tableService.removeTable(-11)
    }

    @Test(expected = UserException::class)
    fun `When get tables of non-existent restaurant, then failure`() {
        tableService.getRestaurantTables(-8)
    }

    @TestConfiguration
    @ComponentScan(basePackageClasses = [Config::class, ModelViewAssembler::class])
    open class Config
}
