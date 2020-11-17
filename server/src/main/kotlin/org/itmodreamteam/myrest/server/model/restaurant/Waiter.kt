package org.itmodreamteam.myrest.server.model.restaurant

import org.itmodreamteam.myrest.server.model.user.User
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "waiters")
class Waiter : Employee {

    constructor() : super()

    constructor(restaurant: Restaurant, user: User) : super(restaurant, user)
}
