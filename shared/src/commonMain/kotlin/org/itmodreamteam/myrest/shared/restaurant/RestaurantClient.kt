package org.itmodreamteam.myrest.shared.restaurant

import org.itmodreamteam.myrest.shared.ContentPage
import org.itmodreamteam.myrest.shared.Pageable
import org.itmodreamteam.myrest.shared.error.ClientException
import kotlin.coroutines.cancellation.CancellationException

@ExperimentalStdlibApi
interface RestaurantClient {

    @Throws(CancellationException::class, ClientException::class)
    suspend fun search(keyword: String, pageable: Pageable): ContentPage<RestaurantInfo>

    @Throws(CancellationException::class, ClientException::class)
    suspend fun register(newRestaurant: RestaurantRegistrationInfo): RestaurantInfo

    @Throws(CancellationException::class, ClientException::class)
    suspend fun update(id: Long, updateInfo: RestaurantUpdateInfo): RestaurantInfo

    @Throws(CancellationException::class, ClientException::class)
    suspend fun searchByAdmin(
        keyword: String,
        statuses: Set<RestaurantStatus>,
        pageable: Pageable
    ): ContentPage<RestaurantInfo>

    @Throws(CancellationException::class, ClientException::class)
    suspend fun updateStatusByAdmin(id: Long, status: RestaurantStatus): RestaurantInfo
}
