package org.itmodreamteam.myrest.shared.restaurant

import org.itmodreamteam.myrest.shared.error.ClientException
import kotlin.coroutines.cancellation.CancellationException

interface EmployeeClient {

    @Throws(CancellationException::class, ClientException::class)
    suspend fun getById(id: Long): EmployeeInfo

    @Throws(CancellationException::class, ClientException::class)
    suspend fun getEmployeesOfRestaurant(restaurantId: Long): List<EmployeeInfo>

    @Throws(CancellationException::class, ClientException::class)
    suspend fun inviteEmployee(restaurantId: Long, invitation: EmployeeInvitation): EmployeeInfo

    @Throws(CancellationException::class, ClientException::class)
    suspend fun updateEmployee(id: Long, status: EmployeeUserStatus): EmployeeInfo

    @Throws(CancellationException::class, ClientException::class)
    suspend fun updateEmployee(id: Long, status: EmployeeRestaurantStatus): EmployeeInfo
}
