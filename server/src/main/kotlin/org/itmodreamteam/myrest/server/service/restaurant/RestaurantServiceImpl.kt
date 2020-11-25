package org.itmodreamteam.myrest.server.service.restaurant

import org.itmodreamteam.myrest.server.error.UserException
import org.itmodreamteam.myrest.server.model.restaurant.Manager
import org.itmodreamteam.myrest.server.model.restaurant.Restaurant
import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.server.repository.restaurant.EmployeeRepository
import org.itmodreamteam.myrest.server.repository.restaurant.RestaurantRepository
import org.itmodreamteam.myrest.server.repository.user.UserRepository
import org.itmodreamteam.myrest.server.service.notification.NotificationService
import org.itmodreamteam.myrest.shared.restaurant.*
import org.itmodreamteam.myrest.shared.user.Role
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class RestaurantServiceImpl(
    private val restaurantRepository: RestaurantRepository,
    private val notificationService: NotificationService,
    private val employeeRepository: EmployeeRepository,
    private val userRepository: UserRepository,
) : RestaurantService {

    override fun register(newRestaurant: RestaurantRegistrationInfo, user: User): RestaurantInfo {
        val existingRestaurant = restaurantRepository.findByName(newRestaurant.name)
        val admins = userRepository.findByRole(Role.ADMIN)
        if (existingRestaurant == null) {
            val restaurant = restaurantRepository.save(Restaurant(newRestaurant))
            admins.forEach {
                notificationService.notify(
                    it,
                    "Ресторан с именем ${restaurant.name} был зарегистрирован и ожидает проверки"
                )
            }
            employeeRepository.save(Manager(restaurant, user))
            return toRestaurantInfo(restaurant)
        } else {
            throw UserException("Ресторан с таким именем уже существует")
        }
    }

    override fun update(id: Long, updateInfo: RestaurantUpdateInfo): RestaurantInfo {
        val existingRestaurant = restaurantRepository.findByIdOrNull(id)
        if (existingRestaurant == null) {
            throw UserException("Такого ресторана не существует")
        } else {
            if (!updateInfo.containsUpdate) {
                throw  UserException("Нечего обновлять")
            }
            return updateRestaurant(existingRestaurant, updateInfo)
        }
    }

    override fun updateStatus(id: Long, restaurantStatus: RestaurantStatus): RestaurantInfo {
        val restaurant = restaurantRepository.findByIdOrNull(id)
            ?: throw UserException("Такого ресторана не существует")
        if (restaurantStatus == restaurant.status) {
            throw  UserException("Статус ресторана не изменился")
        }
        restaurant.status = restaurantStatus
        val savedRestaurant = restaurantRepository.save(restaurant)
        return toRestaurantInfo(savedRestaurant)
    }

    override fun getById(id: Long): RestaurantInfo {
        val restaurant = restaurantRepository.findByIdOrNull(id)
            ?: throw UserException("Такого ресторана не существует")
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

    private fun updateRestaurant(restaurantToUpdate: Restaurant, newInfo: RestaurantUpdateInfo): RestaurantInfo {
        if (newInfo.description != null) {
            restaurantToUpdate.description = newInfo.description!!
        }
        if (!newInfo.legalInfo.isNullOrBlank()) {
            restaurantToUpdate.legalInfo = newInfo.legalInfo!!
        }
        if (!newInfo.email.isNullOrBlank()) {
            restaurantToUpdate.email = newInfo.email!!
        }
        if (!newInfo.phone.isNullOrBlank()) {
            restaurantToUpdate.phone = newInfo.phone!!
        }
        if (!newInfo.websiteUrl.isNullOrBlank()) {
            restaurantToUpdate.websiteUrl = newInfo.websiteUrl!!
        }
        val restaurant = restaurantRepository.save(restaurantToUpdate)
        return toRestaurantInfo(restaurant)
    }
}