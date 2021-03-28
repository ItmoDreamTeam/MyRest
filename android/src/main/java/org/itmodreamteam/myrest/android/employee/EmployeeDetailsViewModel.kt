package org.itmodreamteam.myrest.android.employee

import android.util.Log
import androidx.lifecycle.*
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinLocalDate
import org.itmodreamteam.myrest.shared.error.ClientException
import org.itmodreamteam.myrest.shared.restaurant.EmployeeClient
import org.itmodreamteam.myrest.shared.restaurant.EmployeeInfo
import org.itmodreamteam.myrest.shared.restaurant.ReservationClient
import org.itmodreamteam.myrest.shared.restaurant.ReservationInfo
import java.time.LocalDate

class EmployeeDetailsViewModel @AssistedInject constructor(
    private val employeeClient: EmployeeClient,
    private val reservationClient: ReservationClient,
    @Assisted private var employeeId: Long
) : ViewModel() {

    private val _employee: MutableLiveData<EmployeeInfo> = MutableLiveData()
    val employee: LiveData<EmployeeInfo> = _employee

    private val _reservations: MutableLiveData<List<ReservationInfo>> = MutableLiveData()
    val reservations: LiveData<List<ReservationInfo>> = _reservations

    private val _date: MutableLiveData<LocalDate> = MutableLiveData(LocalDate.now())
    val date: LiveData<LocalDate> = _date

    init {
        viewModelScope.launch {
            _employee.value = employeeClient.getById(employeeId)
            Log.i(javaClass.name, _employee.value.toString())
            fetchReservations()
        }
    }

    fun approve(reservation: ReservationInfo) {
        viewModelScope.launch {
            reservationClient.approve(reservation.id)
            fetchReservations()
        }
    }

    fun reject(reservation: ReservationInfo) {
        viewModelScope.launch {
            reservationClient.reject(reservation.id)
            fetchReservations()
//            val list = mutableListOf<ReservationInfo>()
//            for (reservation in _reservations.value!!) {
//                if (reservation.id == rejected.id) {
//                    list.add(rejected)
//                } else {
//                    list.add(reservation)
//                }
//            }

        }
    }

    private suspend fun fetchReservations() {
        try {
            _reservations.value = reservationClient.getReservationsOfRestaurant(
                employee.value!!.restaurant.id,
                date.value!!.toKotlinLocalDate()
            )
        } catch (e: ClientException) {
            Log.w(javaClass.name, "failed to fetch reservations", e)
        }
    }

    @AssistedInject.Factory
    interface AssistedFactory {
        fun create(employeeId: Long): EmployeeDetailsViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            employeeId: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(employeeId) as T
            }
        }
    }
}

