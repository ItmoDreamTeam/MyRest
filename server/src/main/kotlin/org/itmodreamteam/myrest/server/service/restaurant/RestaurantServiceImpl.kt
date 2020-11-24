package org.itmodreamteam.myrest.server.service.restaurant

import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Manager
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.server.repository.restaurant.EmployeeRepository
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.server.service.notification.NotificationService
import org.itmodreamteam.myrest.shared.restaurant.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class RestaurantServiceImpl(
    private val restaurantRepository: RestaurantRepository,
    private val notificationService: NotificationService,
    private val employeeRepository: EmployeeRepository,
) : RestaurantService {

    override fun register(newRestaurant: RestaurantRegistrationInfo, user: User): RestaurantInfo {
        val existingRestaurant = restaurantRepository.findByName(newRestaurant.name)
        if (existingRestaurant == null) {
            val restaurant = restaurantRepository.save(Restaurant(newRestaurant))
            employeeRepository.save(Manager(restaurant, user))
            notificationService.notify(user, "Ресторан с именем ${restaurant.name} был зарегистрирован и ожидает проверки")
            return toRestaurantInfo(restaurant)
        } else {
            throw UserException("Ресторан с таким именем уже существует")
        }
    }

    override fun update(id: Long, updateInfo: RestaurantUpdateInfo): RestaurantInfo {
        val existingRestaurant = restaurantRepository.findByIdOrNull(id)
        if (existingRestaurant == null) {
            throw UserException("Ресторана с именем $id не существует")
        } else {
            if (!updateInfo.containsUpdate) {
                throw  UserException("Нечего обновлять")
            }
            return updateRestaurant(existingRestaurant, updateInfo)
        }
    }

    override fun updateStatus(id: Long, restaurantStatus: RestaurantStatus) {
        TODO("Not yet implemented")
    }

    override fun getById(id: Long): RestaurantInfo {
        val restaurant = restaurantRepository.findByIdOrNull(id)
            ?: throw UserException("Ресторана с id $id не существует")
        return toRestaurantInfo(restaurant)
    }

    override fun search(keyword: String, statuses: List<RestaurantStatus>, pageable: Pageable): Page<RestaurantInfo> {
        return restaurantRepository.find(keyword, statuses, pageable).map { toRestaurantInfo(it) }
    }

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
            from.externalRating
        )
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
        return toRestaurantInfo(restaurant)
    }
}
