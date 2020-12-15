package org.itmodreamteam.myrest.server.view.assembler

import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.shared.restaurant.RestaurantInfo
import org.springframework.stereotype.Component

@Component
class RestaurantToRestaurantInfoAssembler : ModelViewAssembler<Restaurant, RestaurantInfo> {
    override fun toView(model: Restaurant): RestaurantInfo {
        return RestaurantInfo(
            model.id,
            model.status,
            model.name,
            model.description,
            model.legalInfo,
            model.websiteUrl,
            model.phone,
            model.email,
            model.internalRating,
            model.externalRating
        )
    }
}