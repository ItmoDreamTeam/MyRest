package org.itmodreamteam.myrest.server.model.messaging

import org.itmodreamteam.myrest.server.model.JpaEntity
import org.itmodreamteam.myrest.server.model.user.User
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "notifications")
class Notification() : JpaEntity() {

    @NotNull
    lateinit var title: String
        private set

    @NotNull
    @Size(max = 4000)
    lateinit var text: String
        private set

    @ManyToOne(optional = false)
    lateinit var user: User
        private set

    var seen: Boolean = false

    constructor(title: String, text: String, user: User) : this() {
        this.title = title
        this.text = text
        this.user = user
    }
}
