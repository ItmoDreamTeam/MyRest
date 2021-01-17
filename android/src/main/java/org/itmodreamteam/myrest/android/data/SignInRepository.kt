package org.itmodreamteam.myrest.android.data

import android.content.SharedPreferences
import android.util.Log
import org.itmodreamteam.myrest.shared.error.ClientException
import org.itmodreamteam.myrest.shared.user.*

private val PHONE_PREFERENCE_KEY: String = "phone"
private val ACCESS_TOKEN_KEY: String = "access-token"

class SignInRepository(
    private val userClient: UserClient,
    private val accessTokenMutator: AccessTokenMutator,
    private val sharedPreferences: SharedPreferences
) {
    private var signedInUser: Profile? = null

    var user: Profile? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    fun getLastUsedPhone() : String? {
       return sharedPreferences.getString(PHONE_PREFERENCE_KEY, null)
    }

    suspend fun tryToRecoverSession(): Result<Profile>? {
        val phone  = getLastUsedPhone()
        val accessToken  = sharedPreferences.getString(ACCESS_TOKEN_KEY, null)
        // TODO debug
        Log.i(javaClass.name, "Shared preferences: phone = $phone, access-token = $accessToken")
        if (phone == null || accessToken == null) {
            return null
        }
        accessTokenMutator.setAccessToken(accessToken)
        return try {
            val me = userClient.getMe()
            Log.i(javaClass.name, "Session has been successfully recovered, profile: $me")
            this.signedInUser = me
            return Result.Success(me)
        } catch (e: ClientException) {
            Result.Error(e)
        }
    }

    suspend fun signIn(phone: String): Result<Any> {
        return try {
            userClient.signIn(SignIn(phone))
            sharedPreferences.edit()
                .putString(PHONE_PREFERENCE_KEY, phone)
                .apply()
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
            sharedPreferences.edit()
                .putString(ACCESS_TOKEN_KEY, session.token)
                .apply()
            val me = userClient.getMe()
            this.signedInUser = me
            Result.Success(me)
        } catch (e: ClientException) {
            Result.Error(e)
        }
    }
}