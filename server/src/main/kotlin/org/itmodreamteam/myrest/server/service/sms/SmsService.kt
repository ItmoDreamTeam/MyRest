package org.itmodreamteam.myrest.server.service.sms

interface SmsService {

    fun send(phone: String, text: String)
}
