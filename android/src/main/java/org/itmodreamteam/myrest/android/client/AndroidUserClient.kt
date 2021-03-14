package org.itmodreamteam.myrest.android.client

import org.itmodreamteam.myrest.shared.user.*

class AndroidUserClient(
    private val backingClient: UserClient,
    private val handler: UnauthorizedErrorHandler
) : UserClient {
    override suspend fun getMe(): Profile {
        TODO("Not yet implemented")
    }

    override suspend fun signUp(signUp: SignUp) {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(signIn: SignIn) {
        TODO("Not yet implemented")
    }

    override suspend fun startSession(signInVerification: SignInVerification): ActiveSession {
        TODO("Not yet implemented")
    }

}