package org.itmodreamteam.myrest.server.model.user

import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.JpaEntity
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(
    name = "identifiers", uniqueConstraints = [
        UniqueConstraint(columnNames = ["value"]),
    ]
)
class Identifier() : JpaEntity() {

    @NotNull
    lateinit var value: String
        private set

    @NotNull
    @Enumerated(EnumType.STRING)
    lateinit var type: Type
        private set

    @ManyToOne(optional = false)
    lateinit var user: User
        private set

    var verified: Boolean = false
        private set

    private var verificationCode: String? = null

    private var verificationCodeExpiry: LocalDateTime? = null

    constructor(value: String, type: Type, user: User) : this() {
        this.value = value
        this.type = type
        this.user = user
    }

    fun updateVerificationCode(): String {
        verificationCode = UUID.randomUUID().toString()
        verificationCodeExpiry = now().plusMinutes(5)
        return verificationCode!!
    }

    fun verify(code: String) {
        if (isVerificationCodeExpired() || code != verificationCode) {
            throw UserException("Неверный код")
        }
        verified = true
        verificationCode = null
        verificationCodeExpiry = null
    }

    private fun isVerificationCodeExpired(): Boolean {
        return verificationCodeExpiry?.isBefore(now()) ?: false
    }

    enum class Type {
        PHONE,
        EMAIL,
    }
}
