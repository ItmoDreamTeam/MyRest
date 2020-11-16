package org.itmodreamteam.myrest.server.model.restaurant

import org.itmodreamteam.myrest.server.model.JpaEntity
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.NotNull
import javax.validation.constraints.PositiveOrZero
import javax.validation.constraints.Size

@Entity
@Table(name = "restaurant_tables")
class RestaurantTable() : JpaEntity() {

    @ManyToOne(optional = false)
    lateinit var restaurant: Restaurant
        private set

    @NotNull
    @Size(max = 50)
    lateinit var name: String

    @Size(max = 1000)
    var description: String? = null

    @PositiveOrZero
    var numberOfSeats: Int = 0

    constructor(restaurant: Restaurant, name: String, description: String?, numberOfSeats: Int) : this() {
        this.restaurant = restaurant
        this.name = name
        this.description = description
        this.numberOfSeats = numberOfSeats
    }
}
