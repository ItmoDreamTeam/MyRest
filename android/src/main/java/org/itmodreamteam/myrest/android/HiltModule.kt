package org.itmodreamteam.myrest.android

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import org.itmodreamteam.myrest.android.data.AccessTokenHolder
import org.itmodreamteam.myrest.android.data.AccessTokenMutator
import org.itmodreamteam.myrest.android.data.SignInRepository
import org.itmodreamteam.myrest.shared.AccessTokenProvider
import org.itmodreamteam.myrest.shared.user.UserClient
import org.itmodreamteam.myrest.shared.user.UserClientImpl


@Module
@InstallIn(ActivityComponent::class)
object HiltModule {
    private val accessTokenHolder: AccessTokenHolder = AccessTokenHolder()

    @Provides
    fun accessTokenProvider(): AccessTokenProvider = accessTokenHolder

    @Provides
    fun accessTokenMutator(): AccessTokenMutator = accessTokenHolder

    @Provides
    fun userClient(accessTokenProvider: AccessTokenProvider): UserClient = UserClientImpl(
        accessTokenProvider
    )

    @Provides
    fun signInRepository(
        userClient: UserClient,
        accessTokenMutator: AccessTokenMutator,
        sharedPreferences: SharedPreferences
    ): SignInRepository = SignInRepository(
        userClient,
        accessTokenMutator,
        sharedPreferences
    )

    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences("myrest", Context.MODE_PRIVATE)
    }
}