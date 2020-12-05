package org.itmodreamteam.myrest.server.controller

import org.itmodreamteam.myrest.server.security.CurrentUserService
import org.itmodreamteam.myrest.server.service.restaurant.RestaurantService
import org.itmodreamteam.myrest.shared.ContentPage
import org.itmodreamteam.myrest.shared.Pageable
import org.itmodreamteam.myrest.shared.restaurant.RestaurantInfo
import org.itmodreamteam.myrest.shared.restaurant.RestaurantRegistrationInfo
import org.itmodreamteam.myrest.shared.restaurant.RestaurantStatus
import org.springframework.data.domain.PageRequest
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/restaurants")
class RestaurantController(
    private val restaurantService: RestaurantService,
    private val currentUserService: CurrentUserService,
) {

    @GetMapping
    fun search(keyword: String, pageable: Pageable): ContentPage<RestaurantInfo> {
        val statuses = listOf(RestaurantStatus.ACTIVE)
        val pageRequest = PageRequest.of(pageable.pageNumber, pageable.pageSize)
        return PageUtil.toContentPage(restaurantService.search(keyword, statuses, pageRequest))
    }

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    fun register(@RequestBody newRestaurant: RestaurantRegistrationInfo): RestaurantInfo {
        val user = currentUserService.currentUserEntity
        return restaurantService.register(newRestaurant, user)
    }
}
