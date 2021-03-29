package org.itmodreamteam.myrest.server.service.sms

import org.itmodreamteam.myrest.server.model.JpaEntity
import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Entity
@Table(name = "sms_messages")
class SmsMessage() : JpaEntity() {

    @NotNull
    @Pattern(regexp = "79\\d{9}", message = "sms.phone.incorrect")
    lateinit var phone: String

    @NotNull
    @NotBlank(message = "sms.text.blank")
    @Size(max = 70, message = "sms.text.size")
    lateinit var text: String

    constructor(phone: String, text: String) : this() {
        this.phone = phone
        this.text = text
    }
}
