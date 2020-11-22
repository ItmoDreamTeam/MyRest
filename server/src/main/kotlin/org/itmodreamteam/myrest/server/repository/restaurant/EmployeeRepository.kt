package org.itmodreamteam.myrest.server.repository.restaurant

import org.itmodreamteam.myrest.server.model.restaurant.Employee
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.server.repository.JpaEntityRepository

interface EmployeeRepository : JpaEntityRepository<Employee> {

    fun findByRestaurantAndUser(restaurant: Restaurant, user: User): Employee?

    fun findByRestaurant(restaurant: Restaurant): List<Employee>

    fun findByUser(user: User): List<Employee>
}
