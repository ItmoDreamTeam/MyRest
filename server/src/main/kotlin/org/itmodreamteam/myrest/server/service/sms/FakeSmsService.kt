package org.itmodreamteam.myrest.server.service.sms

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FakeSmsService : SmsService {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun send(phone: String, text: String) {
        log.info("Sending SMS to $phone. Message: $text")
    }
}
