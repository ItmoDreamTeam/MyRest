package org.itmodreamteam.myrest.server.security

import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.server.repository.user.UserRepository
import org.itmodreamteam.myrest.shared.user.Profile
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class CurrentUserServiceImpl(private val userRepository: UserRepository) : CurrentUserService {

    override val currentUser: Profile
        get() {
            val authentication = SecurityContextHolder.getContext().authentication
            if (authentication is UserAuthentication) {
                return authentication.profile
            }
            throw UserException("auth.required")
        }

    override val currentUserEntity: User
        get() = userRepository.findById(currentUser.id).orElseThrow { UserException("auth.required") }
}
