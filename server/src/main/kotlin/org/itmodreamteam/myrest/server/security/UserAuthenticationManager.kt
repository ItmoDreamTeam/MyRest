package org.itmodreamteam.myrest.server.security

import org.itmodreamteam.myrest.server.service.user.UserService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken
import org.springframework.stereotype.Service

@Service
class UserAuthenticationManager(private val userService: UserService) : AuthenticationManager {

    override fun authenticate(authentication: Authentication): Authentication {
        if (authentication !is BearerTokenAuthenticationToken) {
            throw RuntimeException("Unexpected Authentication: $authentication")
        }
        val profile = userService.verifySession(authentication.token)
        return UserAuthentication(profile)
    }
}
