package org.itmodreamteam.myrest.server.model.restaurant

import org.itmodreamteam.myrest.server.model.JpaEntity
import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.shared.restaurant.ReservationStatus
import java.time.LocalDateTime
import java.util.UUID.randomUUID
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(
    name = "reservations", uniqueConstraints = [
        UniqueConstraint(columnNames = ["table_id", "dateTime"])
    ]
)
class Reservation() : JpaEntity() {

    @ManyToOne(optional = false)
    lateinit var user: User
        private set

    @ManyToOne(optional = false)
    lateinit var table: RestaurantTable
        private set

    @NotNull
    lateinit var dateTime: LocalDateTime
        private set

    @NotNull
    lateinit var verificationCode: String
        private set

    @NotNull
    @Enumerated(EnumType.STRING)
    lateinit var status: ReservationStatus

    @ManyToOne
    var manager: Manager? = null

    constructor(user: User, table: RestaurantTable, dateTime: LocalDateTime) : this() {
        this.user = user
        this.table = table
        this.dateTime = dateTime
        this.verificationCode = randomUUID().toString()
        this.status = ReservationStatus.PENDING
    }
}
