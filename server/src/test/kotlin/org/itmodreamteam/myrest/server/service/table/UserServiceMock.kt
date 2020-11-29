package org.itmodreamteam.myrest.server.service.table

import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.server.service.user.UserService
import org.itmodreamteam.myrest.shared.user.*
import org.springframework.stereotype.Service

@Service
class UserServiceMock : UserService {

    override fun toProfile(user: User): Profile {
        return Profile(user.id, user.firstName, user.lastName, user.enabled, user.locked, user.role)
    }

    override fun signUp(signUp: SignUp) {
        TODO("Not yet implemented")
    }

    override fun signIn(signIn: SignIn) {
        TODO("Not yet implemented")
    }

    override fun startSession(signInVerification: SignInVerification): ActiveSession {
        TODO("Not yet implemented")
    }

    override fun verifySession(token: String): Profile {
        TODO("Not yet implemented")
    }
}
