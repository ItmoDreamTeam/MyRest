package org.itmodreamteam.myrest.server.model.user

import org.apache.commons.lang3.RandomStringUtils
import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.JpaEntity
import java.time.LocalDateTime
import java.time.LocalDateTime.now
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

    var verificationCode: String? = null
        private set

    private var verificationCodeExpiry: LocalDateTime? = null

    constructor(value: String, type: Type, user: User) : this() {
        this.value = value
        this.type = type
        this.user = user
    }

    fun updateVerificationCode(): String {
        verificationCode = RandomStringUtils.randomNumeric(6)
        verificationCodeExpiry = now().plusMinutes(5)
        return verificationCode!!
    }

    fun verify(code: String) {
        if (isVerificationCodeExpired() || code != verificationCode) {
            throw UserException("auth.failed")
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
