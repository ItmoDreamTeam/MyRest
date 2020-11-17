package org.itmodreamteam.myrest.server.service.restaurant

import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.server.service.notification.NotificationService
import org.itmodreamteam.myrest.shared.restaurant.RegisterRestaurant
import org.itmodreamteam.myrest.shared.restaurant.UpdateRestaurant
import org.springframework.stereotype.Service

@Service
class RestaurantServiceImpl (
    private val restaurantRepository: RestaurantRepository,
    private val notificationService: NotificationService,
) : RestaurantService {

    override fun registerRestaurant(newRestaurant: RegisterRestaurant) {
        val existingRestaurant = restaurantRepository.findByName(newRestaurant.name)
        if (existingRestaurant == null) {
            restaurantRepository.save(Restaurant(newRestaurant))
            notificationService.notify("Ресторан передан на обработку")
        } else {
            notificationService.notify("Ресторана с таким именем не существует")
            throw UserException("Ресторана с таким именем не существует")
        }
    }

    override fun updateRestaurant(updatedRestaurant: UpdateRestaurant) {
        if (updatedRestaurant.name.isEmpty()) {
            throw UserException("Неправильное имя ресторана")
        }
        if (updatedRestaurant.getProperties().none { !it.isNullOrBlank() }) {
            throw  UserException("Нечего обновлять")
        }
        val existingRestaurant = restaurantRepository.findByName(updatedRestaurant.name)
        if (existingRestaurant == null) {
            notificationService.notify("Невозможно обновить ресторан")
            throw UserException("Ресторана с именем ${updatedRestaurant.name} не существует")
        } else {
            updateRestaurant(existingRestaurant, updatedRestaurant)
        }
    }

    private fun updateRestaurant(restaurant: Restaurant, newInfo: UpdateRestaurant) {
        if (!newInfo.description.isNullOrBlank()) {
            restaurant.description = newInfo.description!!
        }
        if (!newInfo.legalInfo.isNullOrBlank()) {
            restaurant.legalInfo = newInfo.legalInfo!!
        }
        if (!newInfo.email.isNullOrBlank()) {
            restaurant.email = newInfo.email!!
        }
        if (!newInfo.phone.isNullOrBlank()) {
            restaurant.phone = newInfo.phone!!
        }
        if (!newInfo.websiteUrl.isNullOrBlank()) {
            restaurant.websiteUrl = newInfo.websiteUrl!!
        }
        restaurantRepository.save(restaurant)
    }
}