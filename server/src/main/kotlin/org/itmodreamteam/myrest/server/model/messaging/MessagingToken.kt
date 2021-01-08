package org.itmodreamteam.myrest.server.model.messaging

import org.itmodreamteam.myrest.server.model.JpaEntity
import org.itmodreamteam.myrest.server.model.user.User
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.UniqueConstraint
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(
    name = "messaging_tokens", uniqueConstraints = [
        UniqueConstraint(columnNames = ["value"]),
    ]
)
class MessagingToken() : JpaEntity() {

    @NotNull
    @Size(max = 4000)
    lateinit var value: String
        private set

    @ManyToOne(optional = false)
    lateinit var user: User
        private set

    constructor(value: String, user: User) : this() {
        this.value = value
        this.user = user
    }
}
