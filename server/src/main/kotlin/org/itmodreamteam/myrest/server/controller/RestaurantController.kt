package org.itmodreamteam.myrest.server.controller

import org.itmodreamteam.myrest.server.security.CurrentUserService
import org.itmodreamteam.myrest.server.service.employee.EmployeeService
import org.itmodreamteam.myrest.server.service.restaurant.RestaurantService
import org.itmodreamteam.myrest.shared.ContentPage
import org.itmodreamteam.myrest.shared.restaurant.*
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/restaurants")
class RestaurantController(
    private val restaurantService: RestaurantService,
    private val currentUserService: CurrentUserService,
    private val employeeService: EmployeeService,
) {

    @GetMapping
    fun search(keyword: String, pageable: Pageable): ContentPage<RestaurantInfo> {
        val statuses = listOf(RestaurantStatus.ACTIVE)
        return PageUtil.toContentPage(restaurantService.search(keyword, statuses, pageable))
    }

    @GetMapping("/mine")
    @PreAuthorize("isAuthenticated()")
    fun getRestaurantsOfUser(): List<EmployeeInfo> {
        val userId = currentUserService.currentUser.id
        return employeeService.getRestaurantsOfUser(userId)
    }

    @PutMapping
    @PreAuthorize("isAuthenticated()")
    fun register(@RequestBody newRestaurant: RestaurantRegistrationInfo): RestaurantInfo {
        val user = currentUserService.currentUserEntity
        return restaurantService.register(newRestaurant, user)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): RestaurantInfo {
        return restaurantService.getById(id)
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasPermission(#id, 'Restaurant', 'write')")
    fun update(@PathVariable id: Long, @RequestBody updateInfo: RestaurantUpdateInfo): RestaurantInfo {
        return restaurantService.update(id, updateInfo)
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    fun search(keyword: String, statuses: Array<RestaurantStatus>, pageable: Pageable): ContentPage<RestaurantInfo> {
        return PageUtil.toContentPage(restaurantService.search(keyword, statuses.toList(), pageable))
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateStatus(@PathVariable id: Long, @RequestParam status: RestaurantStatus): RestaurantInfo {
        return restaurantService.updateStatus(id, status)
    }
}
