package org.itmodreamteam.myrest.server.model.user

import org.itmodreamteam.myrest.server.model.JpaEntity
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.UUID.randomUUID
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.UniqueConstraint
import javax.validation.constraints.NotNull

@Entity
@Table(
    name = "sessions", uniqueConstraints = [
        UniqueConstraint(columnNames = ["token"]),
    ]
)
class Session() : JpaEntity() {

    @NotNull
    lateinit var token: String
        private set

    @ManyToOne(optional = false)
    lateinit var user: User
        private set

    private var _active: Boolean = false

    @NotNull
    private lateinit var expiry: LocalDateTime

    var active: Boolean
        get() = _active && now().isBefore(expiry)
        set(value) {
            _active = value
        }

    constructor(user: User) : this() {
        this.user = user
        this.token = randomUUID().toString()
        this._active = true
        this.expiry = now().plusYears(1)
    }
}
