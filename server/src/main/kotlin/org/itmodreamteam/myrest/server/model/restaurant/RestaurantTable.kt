package org.itmodreamteam.myrest.server.model.restaurant

import org.itmodreamteam.myrest.server.model.JpaEntity
import javax.persistence.*
import javax.validation.constraints.Positive
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

    @Positive(message = "Некорректный номер столика")
    var number: Int = 0

    @Size(max = 1000)
    var description: String? = null

    @Positive(message = "Некорректное число мест")
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
