package org.itmodreamteam.myrest.server.service.restaurant

import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.server.service.notification.NotificationService
import org.itmodreamteam.myrest.shared.restaurant.RegisterRestaurant
import org.springframework.stereotype.Service

@Service
class RestaurantServiceImpl (
    private val restaurantRepository: RestaurantRepository,
    private val notificationService: NotificationService
) : RestaurantService {

    override fun registerRestaurant(newRestaurant: RegisterRestaurant) {
        val existingRestaurant = restaurantRepository.findByName(newRestaurant.name)
        if (existingRestaurant == null) {
            restaurantRepository.save(Restaurant(newRestaurant))
            notificationService.notify("Ресторан передан на обработку")
        } else {
            notificationService.notify("Ресторан с таким именем существует")
            throw UserException("Ресторан с таким именем существует")
        }
    }
}