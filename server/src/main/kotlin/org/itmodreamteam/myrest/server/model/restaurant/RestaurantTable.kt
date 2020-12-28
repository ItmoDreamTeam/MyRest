package org.itmodreamteam.myrest.server.model.restaurant

import org.itmodreamteam.myrest.server.model.JpaEntity
import javax.persistence.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.Size

@Entity
@Table(
    name = "restaurant_tables", uniqueConstraints = [
        UniqueConstraint(columnNames = ["restaurant_id", "number"])
    ]
)
class RestaurantTable() : JpaEntity() {

    @ManyToOne(optional = false)
    lateinit var restaurant: Restaurant
        private set

    @Min(1, message = "table.number.incorrect")
    @Max(100, message = "table.number.incorrect")
    var number: Int = 0

    @Size(max = 1000, message = "table.description.size")
    var description: String? = null

    @Min(1, message = "table.number-of-seats.incorrect")
    @Max(100, message = "table.number-of-seats.incorrect")
    var numberOfSeats: Int = 0

    @ManyToMany
    private lateinit var _waiters: MutableSet<Waiter>

    var waiters: List<Waiter>
        get() = _waiters.sortedBy { it.user }
        private set(value) {}

    constructor(restaurant: Restaurant, number: Int, description: String?, numberOfSeats: Int) : this() {
        this.restaurant = restaurant
        this.number = number
        this.description = description
        this.numberOfSeats = numberOfSeats
        this._waiters = mutableSetOf()
    }

    fun addWaiter(waiter: Waiter) = _waiters.add(waiter)

    fun removeWaiter(waiter: Waiter) = _waiters.remove(waiter)
}
