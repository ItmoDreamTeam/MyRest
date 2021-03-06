package org.itmodreamteam.myrest.server.model.user

import org.itmodreamteam.myrest.server.model.JpaEntity
import org.itmodreamteam.myrest.shared.user.Role
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "users")
class User() : JpaEntity(), Comparable<User> {

    var enabled: Boolean = false

    var locked: Boolean = false

    @NotNull
    lateinit var firstName: String

    @NotNull
    lateinit var lastName: String

    @Enumerated(EnumType.STRING)
    var role: Role? = null

    constructor(firstName: String, lastName: String) : this() {
        this.firstName = firstName
        this.lastName = lastName
    }

    override fun compareTo(other: User): Int {
        return Comparator.comparing(User::firstName)
            .thenComparing(User::lastName)
            .compare(this, other)
    }
}
