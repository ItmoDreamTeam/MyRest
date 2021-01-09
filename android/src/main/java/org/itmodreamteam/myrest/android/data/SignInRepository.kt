package org.itmodreamteam.myrest.android.data

import org.itmodreamteam.myrest.shared.error.ClientException
import org.itmodreamteam.myrest.shared.user.*

// TODO persistence
class SignInRepository(
    private val userClient: UserClient,
    private val accessTokenMutator: AccessTokenMutator
) {
    private var session: ActiveSession? = null
    private var signedInUser: Profile? = null

    var user: Profile? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    suspend fun signIn(phone: String): Result<Any> {
        return try {
            userClient.signIn(SignIn(phone))
            // TODO passing 1 is not clear, how to return Void?
            return Result.Success(1)
        } catch (e: ClientException) {
            Result.Error(e)
        }
    }

    suspend fun startSession(phone: String, code: String): Result<Profile> {
        return try {
            val session = userClient.startSession(SignInVerification(phone, code))
            accessTokenMutator.setAccessToken(session.token)
            this.session = session
            val me = userClient.getMe()
            this.signedInUser = me
            Result.Success(me)
        } catch (e: ClientException) {
            Result.Error(e)
        }
    }
}