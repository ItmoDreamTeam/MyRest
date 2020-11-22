package org.itmodreamteam.myrest.server.model.restaurant

import org.itmodreamteam.myrest.server.model.JpaEntity
import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

@Entity
@Table(name = "restaurant_tables")
class RestaurantTable() : JpaEntity() {

    @ManyToOne(optional = false)
    lateinit var restaurant: Restaurant
        private set

    @NotNull(message = "Введите название столика")
    @NotBlank(message = "Введите название столика")
    @Size(max = 50)
    lateinit var name: String

    @Size(max = 1000)
    var description: String? = null

    @Positive(message = "Некорректное число мест")
    var numberOfSeats: Int = 0

    @ManyToMany
    private lateinit var _waiters: MutableSet<Waiter>

    var waiters: List<Waiter>
        get() = _waiters.sortedBy { it.user }
        private set(value) {}

    constructor(restaurant: Restaurant, name: String, description: String?, numberOfSeats: Int) : this() {
        this.restaurant = restaurant
        this.name = name
        this.description = description
        this.numberOfSeats = numberOfSeats
        this._waiters = mutableSetOf()
    }
}
