package org.itmodreamteam.myrest.server.service.sms

import com.fasterxml.jackson.annotation.JsonProperty
import org.itmodreamteam.myrest.server.error.UserException
import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.web.client.postForEntity
import java.nio.charset.Charset
import java.time.*

@Service
@Primary
@Profile("prod")
class RealSmsService(private var smsMessageRepository: SmsMessageRepository) : SmsService {

    companion object {
        private val MIN_INTERVAL_BETWEEN_MESSAGES = Duration.ofMinutes(5)
    }

    private val log = LoggerFactory.getLogger(javaClass)

    private val restTemplate = RestTemplateBuilder()
        .rootUri("https://rest-api.d7networks.com/secure")
        .basicAuthentication(System.getenv("SMS_SERVICE_USERNAME"), System.getenv("SMS_SERVICE_PASSWORD"))
        .build()

    override fun send(phone: String, text: String) {
        log.info("Sending SMS to $phone. Message: $text")
        checkDailyLimit()
        checkFrequencyLimit()
        val message = registerMessage(phone, text)
        executeSending(message)
    }

    private fun checkDailyLimit() {
        val periodPassed = Period.between(LocalDate.of(2020, Month.DECEMBER, 22), LocalDate.now())
        val currentLimit = 10 * periodPassed.days
        val sent = smsMessageRepository.count()
        log.info("SMS sent: $sent, current limit: $currentLimit")
        if (sent >= currentLimit) {
            throw UserException("sms.limit.daily")
        }
    }

    private fun checkFrequencyLimit() {
        val pageable = PageRequest.of(0, 1, Sort.by("created").descending())
        val history = smsMessageRepository.findAll(pageable).content
        if (history.isNotEmpty()) {
            val timePassed = Duration.between(history.first().created, LocalDateTime.now())
            if (timePassed < MIN_INTERVAL_BETWEEN_MESSAGES) {
                throw UserException("sms.limit.frequency")
            }
        }
    }

    private fun registerMessage(phone: String, text: String): SmsMessage {
        return smsMessageRepository.save(SmsMessage(phone, text))
    }

    private fun executeSending(message: SmsMessage) {
        try {
            restTemplate.postForEntity<Any>("/send", SmsRequest.cyrillic(message.phone, message.text))
        } catch (e: Exception) {
            log.error("Unable to send SMS: ${e.message}", e)
            throw UserException("sms.failure")
        }
    }

    private data class SmsRequest(
        val to: String,
        @field:JsonProperty("hex-content")
        val hexContent: String,
        val coding: Int,
        val from: String = "MyRest",
    ) {
        private data class Coding(val name: String, val code: Int)

        companion object {
            private val CODING_CYRILLIC = Coding("ISO-8859-5", 6)

            fun cyrillic(phone: String, text: String): SmsRequest {
                val bytes = text.toByteArray(Charset.forName(CODING_CYRILLIC.name))
                val hex = bytes.joinToString(separator = "") { it.toUByte().toString(16) }
                return SmsRequest(phone, hex, CODING_CYRILLIC.code)
            }
        }
    }
}
