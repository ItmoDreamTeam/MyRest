package org.itmodreamteam.myrest.server.model.user

import org.itmodreamteam.myrest.server.model.JpaEntity
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "users")
class User : JpaEntity() {

    var enabled: Boolean = false

    var locked: Boolean = false

    @NotNull
    lateinit var firstName: String

    @NotNull
    lateinit var lastName: String

    @Enumerated(EnumType.STRING)
    var role: Role? = null

    enum class Role {
        ADMIN,
    }
}
