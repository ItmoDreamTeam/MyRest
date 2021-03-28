package org.itmodreamteam.myrest.shared.restaurant

import io.ktor.client.request.*
import org.itmodreamteam.myrest.shared.AccessTokenProvider.Companion.provideAccessToken
import org.itmodreamteam.myrest.shared.HttpClientProvider

class EmployeeClientImpl : EmployeeClient {

    private val client = HttpClientProvider.provide()

    override suspend fun getById(id: Long): EmployeeInfo {
        return client.get {
            url("/employees/$id")
            provideAccessToken()
        }
    }

    override suspend fun getEmployeesOfRestaurant(restaurantId: Long): List<EmployeeInfo> {
        return client.get {
            url("/restaurants/$restaurantId/employees")
            provideAccessToken()
        }
    }

    override suspend fun inviteEmployee(restaurantId: Long, invitation: EmployeeInvitation): EmployeeInfo {
        return client.put {
            url("/restaurants/$restaurantId/employees")
            provideAccessToken()
            body = invitation
        }
    }

    override suspend fun updateEmployee(id: Long, status: EmployeeUserStatus): EmployeeInfo {
        return client.put {
            url("/employees/$id")
            parameter("status", status)
            provideAccessToken()
        }
    }

    override suspend fun updateEmployee(id: Long, status: EmployeeRestaurantStatus): EmployeeInfo {
        return client.put {
            url("/employees/$id/manager")
            parameter("status", status)
            provideAccessToken()
        }
    }
}
