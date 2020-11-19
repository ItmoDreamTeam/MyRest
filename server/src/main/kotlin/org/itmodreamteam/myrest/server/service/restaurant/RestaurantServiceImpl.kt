package org.itmodreamteam.myrest.server.service.restaurant

import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.shared.restaurant.RestaurantInfo
import org.itmodreamteam.myrest.shared.restaurant.RestaurantRegistrationInfo
import org.itmodreamteam.myrest.shared.restaurant.RestaurantStatus
import org.itmodreamteam.myrest.shared.restaurant.RestaurantUpdateInfo
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.validation.ConstraintViolationException

@Service
class RestaurantServiceImpl (
    private val restaurantRepository: RestaurantRepository,
) : RestaurantService {

    override fun register(newRestaurant: RestaurantRegistrationInfo): RestaurantInfo {
        val existingRestaurant = restaurantRepository.findByName(newRestaurant.name)
        if (existingRestaurant == null) {
            try {
                val restaurant = restaurantRepository.save(Restaurant(newRestaurant))
                return RestaurantInfo(
                    restaurant.id,
                    restaurant.status,
                    restaurant.name,
                    restaurant.description,
                    restaurant.legalInfo,
                    restaurant.websiteUrl,
                    restaurant.phone,
                    restaurant.email,
                    restaurant.internalRating,
                    restaurant.externalRating
                )
            } catch (e: ConstraintViolationException) {
                throw UserException("Неверное значение поля")
            }
        } else {
            throw UserException("Ресторан с таким именем уже существует")
        }
    }

    override fun update(restaurantUpdateInfo: RestaurantUpdateInfo): RestaurantInfo {
        val existingRestaurant = restaurantRepository.findByIdOrNull(restaurantUpdateInfo.id)
        if (existingRestaurant == null) {
            throw UserException("Ресторана с именем ${restaurantUpdateInfo.id} не существует")
        } else {
            if (restaurantUpdateInfo.containsUpdate) {
                throw  UserException("Нечего обновлять")
            }
            return updateRestaurant(existingRestaurant, restaurantUpdateInfo)
        }
    }

    private fun updateRestaurant(restaurant: Restaurant, newInfo: RestaurantUpdateInfo): RestaurantInfo {
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
        val restaurant = restaurantRepository.save(restaurant)
        return RestaurantInfo(
            restaurant.id,
            restaurant.status,
            restaurant.name,
            restaurant.description,
            restaurant.legalInfo,
            restaurant.websiteUrl,
            restaurant.phone,
            restaurant.email,
            restaurant.internalRating,
            restaurant.externalRating
        )
    }

    override fun getById(id: Long): RestaurantInfo {
        val restaurant = restaurantRepository.findByIdOrNull(id) ?: throw UserException("Ресторана с id $id не существует")
        return RestaurantInfo(
            restaurant.id,
            restaurant.status,
            restaurant.name,
            restaurant.description,
            restaurant.legalInfo,
            restaurant.websiteUrl,
            restaurant.phone,
            restaurant.email,
            restaurant.internalRating,
            restaurant.externalRating
        )
    }

    override fun search(keyword: String, statuses: List<RestaurantStatus>, pageable: Pageable): Page<RestaurantInfo> {
        return PageImpl(restaurantRepository.find(keyword, statuses, pageable).content.map {
            RestaurantInfo(
                it.id,
                it.status,
                it.name,
                it.description,
                it.legalInfo,
                it.websiteUrl,
                it.phone,
                it.email,
                it.internalRating,
                it.externalRating
            )
        })
    }
}