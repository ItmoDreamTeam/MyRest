package org.itmodreamteam.myrest.server.model.restaurant

import org.itmodreamteam.myrest.server.model.user.User
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "managers")
class Manager : Employee {

    constructor() : super()

    constructor(restaurant: Restaurant, user: User) : super(restaurant, user)
}
