package org.itmodreamteam.myrest.server.security

import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.shared.user.Profile

interface CurrentUserService {
    val currentUser: Profile
    val currentUserEntity: User
}
