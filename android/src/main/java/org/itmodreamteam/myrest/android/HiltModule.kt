package org.itmodreamteam.myrest.android

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
    fun userClient(accessTokenProvider: AccessTokenProvider): UserClient = UserClientImpl(accessTokenProvider)

    @Provides
    fun signInRepository(userClient: UserClient, accessTokenMutator: AccessTokenMutator): SignInRepository = SignInRepository(userClient, accessTokenMutator)
}