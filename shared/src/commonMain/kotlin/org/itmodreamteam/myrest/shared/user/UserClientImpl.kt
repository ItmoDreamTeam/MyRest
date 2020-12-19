package org.itmodreamteam.myrest.shared.user

import io.ktor.client.request.*
import org.itmodreamteam.myrest.shared.AccessTokenProvider.Companion.provideAccessToken
import org.itmodreamteam.myrest.shared.HttpClientProvider

@ExperimentalStdlibApi
class UserClientImpl : UserClient {

    private val client = HttpClientProvider.provide()

    override suspend fun getMe(): Profile {
        return client.get {
            url("/users/me")
            provideAccessToken()
        }
    }

    override suspend fun signUp(signUp: SignUp) {
        return client.post {
            url("/users/sign-up")
            body = signUp
        }
    }

    override suspend fun signIn(signIn: SignIn) {
        return client.post {
            url("/users/sign-in")
            body = signIn
        }
    }

    override suspend fun startSession(signInVerification: SignInVerification): ActiveSession {
        return client.put {
            url("/users/sessions")
            body = signInVerification
        }
    }
}
