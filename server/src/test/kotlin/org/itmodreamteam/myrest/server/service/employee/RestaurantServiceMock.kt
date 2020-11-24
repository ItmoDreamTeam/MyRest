package org.itmodreamteam.myrest.server.service.employee

import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.server.service.restaurant.RestaurantService
import org.itmodreamteam.myrest.shared.restaurant.RestaurantInfo
import org.itmodreamteam.myrest.shared.restaurant.RestaurantRegistrationInfo
import org.itmodreamteam.myrest.shared.restaurant.RestaurantStatus
import org.itmodreamteam.myrest.shared.restaurant.RestaurantUpdateInfo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class RestaurantServiceMock : RestaurantService {

    override fun toRestaurantInfo(from: Restaurant): RestaurantInfo {
        return RestaurantInfo(
            from.id,
            from.status,
            from.name,
            from.description,
            from.legalInfo,
            from.websiteUrl,
            from.phone,
            from.email,
            from.internalRating,
            from.externalRating,
        )
    }

    override fun register(newRestaurant: RestaurantRegistrationInfo, user: User): RestaurantInfo {
        TODO("Not yet implemented")
    }

    override fun update(id: Long, updateInfo: RestaurantUpdateInfo): RestaurantInfo {
        TODO("Not yet implemented")
    }

    override fun getById(id: Long): RestaurantInfo {
        TODO("Not yet implemented")
    }

    override fun search(keyword: String, statuses: List<RestaurantStatus>, pageable: Pageable): Page<RestaurantInfo> {
        TODO("Not yet implemented")
    }
}
