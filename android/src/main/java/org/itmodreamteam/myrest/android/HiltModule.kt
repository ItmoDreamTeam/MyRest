package org.itmodreamteam.myrest.android

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import org.itmodreamteam.myrest.android.data.AccessTokenHolder
import org.itmodreamteam.myrest.android.data.AccessTokenMutator
import org.itmodreamteam.myrest.android.data.SignInRepository
import org.itmodreamteam.myrest.shared.AccessTokenProvider
import org.itmodreamteam.myrest.shared.restaurant.ReservationClient
import org.itmodreamteam.myrest.shared.restaurant.ReservationClientImpl
import org.itmodreamteam.myrest.shared.restaurant.RestaurantClient
import org.itmodreamteam.myrest.shared.restaurant.RestaurantClientImpl
import org.itmodreamteam.myrest.shared.table.TableClient
import org.itmodreamteam.myrest.shared.table.TableClientImpl
import org.itmodreamteam.myrest.shared.user.UserClient
import org.itmodreamteam.myrest.shared.user.UserClientImpl
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object HiltModule {
    private val accessTokenHolder: AccessTokenHolder = AccessTokenHolder()

    @Provides
    @Singleton
    fun accessTokenProvider(): AccessTokenProvider = accessTokenHolder

    @Provides
    @Singleton
    fun accessTokenMutator(): AccessTokenMutator = accessTokenHolder

    @Provides
    @Singleton
    fun userClient(accessTokenProvider: AccessTokenProvider): UserClient = UserClientImpl(
        accessTokenProvider
    )

    @Provides
    @Singleton
    fun restaurantClient(accessTokenProvider: AccessTokenProvider): RestaurantClient = RestaurantClientImpl(
        accessTokenProvider
    )

    @Provides
    @Singleton
    fun reservationClient(accessTokenProvider: AccessTokenProvider): ReservationClient = ReservationClientImpl(accessTokenProvider)

    @Provides
    @Singleton
    fun tableClient(accessTokenProvider: AccessTokenProvider): TableClient = TableClientImpl(accessTokenProvider)

    @Provides
    @Singleton
    fun signInRepository(
        userClient: UserClient,
        accessTokenMutator: AccessTokenMutator,
        sharedPreferences: SharedPreferences
    ): SignInRepository {
        return SignInRepository(
            userClient,
            accessTokenMutator,
            sharedPreferences
        )
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences("myrest", Context.MODE_PRIVATE)
    }
}