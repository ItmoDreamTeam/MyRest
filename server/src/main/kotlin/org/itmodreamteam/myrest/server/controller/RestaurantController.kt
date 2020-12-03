package org.itmodreamteam.myrest.server.controller

import org.itmodreamteam.myrest.server.service.restaurant.RestaurantService
import org.itmodreamteam.myrest.shared.ContentPage
import org.itmodreamteam.myrest.shared.Pageable
import org.itmodreamteam.myrest.shared.restaurant.RestaurantInfo
import org.itmodreamteam.myrest.shared.restaurant.RestaurantStatus
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/restaurants")
class RestaurantController(private val restaurantService: RestaurantService) {

    @GetMapping
    fun search(keyword: String, pageable: Pageable): ContentPage<RestaurantInfo> {
        val statuses = listOf(RestaurantStatus.ACTIVE)
        val pageRequest = PageRequest.of(pageable.pageNumber, pageable.pageSize)
        return PageUtil.toContentPage(restaurantService.search(keyword, statuses, pageRequest))
    }
}
