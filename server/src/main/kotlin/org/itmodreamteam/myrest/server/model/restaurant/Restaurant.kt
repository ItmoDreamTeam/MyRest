package org.itmodreamteam.myrest.server.model.restaurant

import org.itmodreamteam.myrest.server.model.JpaEntity
import org.itmodreamteam.myrest.shared.restaurant.RestaurantRegistrationInfo
import org.itmodreamteam.myrest.shared.restaurant.RestaurantStatus
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(
    name = "restaurants",
    uniqueConstraints = [UniqueConstraint(columnNames = ["name"])]
)
class Restaurant() : JpaEntity() {

    @NotNull
    @Enumerated(EnumType.STRING)
    lateinit var status: RestaurantStatus

    @NotNull
    @NotBlank(message = "restaurant.name.blank")
    @Size(min = 2, max = 50, message = "restaurant.name.size")
    lateinit var name: String

    @NotNull
    @NotBlank(message = "restaurant.description.blank")
    @Size(max = 10000, message = "restaurant.description.size")
    lateinit var description: String

    @NotNull
    @NotBlank(message = "restaurant.legal-info.blank")
    @Size(max = 1000, message = "restaurant.legal-info.size")
    lateinit var legalInfo: String

    @Size(max = 200)
    var websiteUrl: String? = null

    @Size(max = 50)
    var phone: String? = null

    @Size(max = 50)
    var email: String? = null

    var internalRating: Double = 0.0

    var externalRating: Double = 0.0

    constructor(name: String, description: String, legalInfo: String) : this() {
        this.name = name
        this.description = description
        this.legalInfo = legalInfo
        this.status = RestaurantStatus.PENDING
    }

    constructor(registerRestaurant: RestaurantRegistrationInfo) : this() {
        this.name = registerRestaurant.name
        this.description = registerRestaurant.description
        this.legalInfo = registerRestaurant.legalInfo
        this.websiteUrl = registerRestaurant.websiteUrl
        this.phone = registerRestaurant.phone
        this.email = registerRestaurant.email
        this.status = RestaurantStatus.PENDING
    }
}
