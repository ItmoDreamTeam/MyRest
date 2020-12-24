package org.itmodreamteam.myrest.server.model.restaurant

import org.itmodreamteam.myrest.server.model.JpaEntity
import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.shared.restaurant.ReservationStatus
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "reservations")
class Reservation() : JpaEntity() {

    @ManyToOne(optional = false)
    lateinit var user: User
        private set

    @ManyToOne(optional = false)
    lateinit var table: RestaurantTable
        private set

    @NotNull
    lateinit var activeFrom: LocalDateTime
        private set

    @NotNull
    lateinit var activeUntil: LocalDateTime
        private set

    @NotNull
    @Enumerated(EnumType.STRING)
    lateinit var status: ReservationStatus

    @ManyToOne
    var manager: Manager? = null

    constructor(user: User, table: RestaurantTable, activeFrom: LocalDateTime, activeUntil: LocalDateTime) : this() {
        this.user = user
        this.table = table
        this.activeFrom = activeFrom
        this.activeUntil = activeUntil
        this.status = ReservationStatus.PENDING
    }
}
