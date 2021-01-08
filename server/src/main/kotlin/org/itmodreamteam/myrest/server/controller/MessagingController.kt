package org.itmodreamteam.myrest.server.controller

import org.itmodreamteam.myrest.server.security.CurrentUserService
import org.itmodreamteam.myrest.server.service.messaging.MessagingService
import org.itmodreamteam.myrest.shared.messaging.MessagingTokenRegistration
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/messaging")
class MessagingController(
    private val messagingService: MessagingService,
    private val currentUserService: CurrentUserService,
) {

    @PutMapping("/token")
    @PreAuthorize("isAuthenticated()")
    fun registerMessagingToken(@RequestBody registration: MessagingTokenRegistration) {
        val user = currentUserService.currentUserEntity
        return messagingService.registerMessagingToken(user, registration)
    }
}
