package org.itmodreamteam.myrest.android

import android.app.Application
import org.itmodreamteam.myrest.android.data.AccessTokenHolder
import org.itmodreamteam.myrest.android.data.SignInRepository
import org.itmodreamteam.myrest.shared.user.UserClient
import org.itmodreamteam.myrest.shared.user.UserClientImpl

// TODO manual DI. https://developer.android.com/training/dependency-injection/manual
class MyRestApplication : Application() {
    private val _accessTokenHolder: AccessTokenHolder = AccessTokenHolder()
    val userClient: UserClient = UserClientImpl(_accessTokenHolder)
    val signInRepository: SignInRepository = SignInRepository(userClient, _accessTokenHolder)
}