package org.itmodreamteam.myrest.android.data

import android.content.SharedPreferences
import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.itmodreamteam.myrest.shared.error.ClientException
import org.itmodreamteam.myrest.shared.error.ServerError
import org.itmodreamteam.myrest.shared.user.*
import java.util.concurrent.TimeUnit

private val PHONE_PREFERENCE_KEY: String = "phone"
private val ACCESS_TOKEN_KEY: String = "access-token"


class SignInRepository(
    private val userClient: UserClient,
    private val accessTokenMutator: AccessTokenMutator,
    private val sharedPreferences: SharedPreferences
) {
    private val _signedInUser: MutableLiveData<Profile> = MutableLiveData()
    val signedInUser: LiveData<Profile> = _signedInUser

    val employeeMode: MutableLiveData<Boolean> = MutableLiveData(false)

    val isLoggedIn: Boolean
        get() = _signedInUser.value != null

    private val _nextAllowedSignInAt : MutableLiveData<Long> = MutableLiveData(SystemClock.elapsedRealtime())

    val nextAllowedSignInAt : LiveData<Long> = _nextAllowedSignInAt

    fun getLastUsedPhone() : String? {
       return sharedPreferences.getString(PHONE_PREFERENCE_KEY, null)
    }

    fun logout() {
        _signedInUser.value = null
        accessTokenMutator.removeAccessToken()
        sharedPreferences.edit()
            .remove(ACCESS_TOKEN_KEY)
            .apply()
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
            this._signedInUser.value = me
            return Result.Success(me)
        } catch (e: ClientException) {
            Result.Error(e)
        }
    }

    suspend fun signIn(phone: String): Result<Any> {
        val diff = TimeUnit.MILLISECONDS.toSeconds(nextAllowedSignInAt.value!! - SystemClock.elapsedRealtime())
        if (diff > 0) {
            return Result.Error(exception = ClientException(listOf(ServerError("", "Отправка кода будет доступна через $diff секунд"))))
        }
        _nextAllowedSignInAt.value = SystemClock.elapsedRealtime() + TimeUnit.SECONDS.toMillis(60)
        val signIn = justSignIn(phone)
        if (signIn is Result.Error) {
            if (signIn.exception.errors.any { it.key == "user.phone.not-found" }) {
                Log.i(
                    javaClass.name,
                    "Phone not found, try to bypass sign up",
                    signIn.exception
                )
                return justSignUp(phone)
            }
        }
        return signIn
    }

    suspend fun updateProfile(firstName: String, lastName: String): Result<Profile> {
        return try {
            val me = userClient.update(ProfilePatch(firstName, lastName))
            this._signedInUser.value = me
            return Result.Success(me)
        } catch (e: ClientException) {
            Result.Error(e)
        }
    }

    private suspend fun justSignIn(phone: String): Result<Any> {
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

    private suspend fun justSignUp(phone: String): Result<Any> {
        return try {
            userClient.signUp(SignUp("", "", phone))
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
            this._signedInUser.value = me
            Result.Success(me)
        } catch (e: ClientException) {
            Result.Error(e)
        }
    }
}