package org.itmodreamteam.myrest.server.service.restaurant

import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.shared.restaurant.RestaurantRegistrationInfo
import org.itmodreamteam.myrest.shared.restaurant.RestaurantUpdateInfo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class RestaurantServiceImpl (
    private val restaurantRepository: RestaurantRepository,
) : RestaurantService {

    override fun register(newRestaurant: RestaurantRegistrationInfo) {
        val existingRestaurant = restaurantRepository.findByName(newRestaurant.name)
        if (existingRestaurant == null) {
            restaurantRepository.save(Restaurant(newRestaurant))
        } else {
            throw UserException("Ресторан с таким именем уже существует")
        }
    }

    override fun update(restaurantUpdateInfo: RestaurantUpdateInfo) {
        if (restaurantUpdateInfo.name.isEmpty()) {
            throw UserException("Неправильное имя ресторана")
        }
        if (restaurantUpdateInfo.getProperties().all { it.isNullOrBlank() }) {
            throw  UserException("Нечего обновлять")
        }
        val existingRestaurant = restaurantRepository.findByName(restaurantUpdateInfo.name)
        if (existingRestaurant == null) {
            throw UserException("Ресторана с именем ${restaurantUpdateInfo.name} не существует")
        } else {
            updateRestaurant(existingRestaurant, restaurantUpdateInfo)
        }
    }

    private fun updateRestaurant(restaurant: Restaurant, newInfo: RestaurantUpdateInfo) {

        if (newInfo.description != null) {
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

    override fun getById(id: Long) : Restaurant {
        return restaurantRepository.findByIdOrNull(id) ?: throw UserException("Ресторана с id $id не существует")
    }

    override fun search(keyword: String, pageable: Pageable) : Page<Restaurant> {
        return restaurantRepository.findByNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase(keyword, keyword,Pageable.unpaged())
    }
}