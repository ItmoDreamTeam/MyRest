package org.itmodreamteam.myrest.server.service.sms

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class StubSmsService : SmsService {

    private val log = LoggerFactory.getLogger(StubSmsService::class.java)

    override fun send(phone: String, text: String) {
        log.info("Sending SMS to $phone. Message: $text")
    }
}
