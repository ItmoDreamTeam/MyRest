package org.itmodreamteam.myrest.server.model.restaurant

import org.itmodreamteam.myrest.server.model.JpaEntity
import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.shared.restaurant.EmployeeRestaurantStatus
import org.itmodreamteam.myrest.shared.restaurant.EmployeeUserStatus
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(
    name = "employees", uniqueConstraints = [
        UniqueConstraint(columnNames = ["restaurant_id", "user_id"]),
    ]
)
abstract class Employee() : JpaEntity() {

    @ManyToOne(optional = false)
    lateinit var restaurant: Restaurant
        private set

    @ManyToOne(optional = false)
    lateinit var user: User
        private set

    @NotNull
    @Enumerated(EnumType.STRING)
    lateinit var userStatus: EmployeeUserStatus

    @NotNull
    @Enumerated(EnumType.STRING)
    lateinit var restaurantStatus: EmployeeRestaurantStatus

    constructor(restaurant: Restaurant, user: User) : this() {
        this.restaurant = restaurant
        this.user = user
        this.userStatus = EmployeeUserStatus.PENDING
        this.restaurantStatus = EmployeeRestaurantStatus.ACTIVE
    }
}
