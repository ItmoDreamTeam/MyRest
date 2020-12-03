package org.itmodreamteam.myrest.server.security

import org.itmodreamteam.myrest.shared.user.Profile

interface CurrentUserService {
    val currentUser: Profile
}
