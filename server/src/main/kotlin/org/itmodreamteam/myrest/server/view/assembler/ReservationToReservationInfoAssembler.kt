package org.itmodreamteam.myrest.server.view.assembler

import kotlinx.datetime.toKotlinLocalDateTime
import org.itmodreamteam.myrest.server.model.restaurant.Employee
import org.itmodreamteam.myrest.server.model.restaurant.Reservation
import org.itmodreamteam.myrest.server.model.restaurant.RestaurantTable
import org.itmodreamteam.myrest.server.model.user.User
import org.itmodreamteam.myrest.shared.restaurant.EmployeeInfo
import org.itmodreamteam.myrest.shared.restaurant.ReservationInfo
import org.itmodreamteam.myrest.shared.table.TableView
import org.itmodreamteam.myrest.shared.user.Profile
import org.springframework.stereotype.Component

@Component
class ReservationToReservationInfoAssembler(
    private val userToProfileAssembler: ModelViewAssembler<User, Profile>,
    private val tableToTableViewAssembler: ModelViewAssembler<RestaurantTable, TableView>,
    private val employeeToEmployeeInfoAssembler: ModelViewAssembler<Employee, EmployeeInfo>
) : ModelViewAssembler<Reservation, ReservationInfo> {
    override fun toView(model: Reservation): ReservationInfo {
        return ReservationInfo(
            model.id,
            userToProfileAssembler.toView(model.user),
            tableToTableViewAssembler.toView(model.table),
            if (model.manager != null) employeeToEmployeeInfoAssembler.toView(model.manager!!) else null,
            model.status,
            model.activeFrom.toKotlinLocalDateTime(),
            model.activeUntil.toKotlinLocalDateTime()
        )
    }
}