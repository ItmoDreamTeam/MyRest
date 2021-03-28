package org.itmodreamteam.myrest.android.employee

import android.util.Log
import androidx.lifecycle.*
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinLocalDate
import org.itmodreamteam.myrest.shared.error.ClientException
import org.itmodreamteam.myrest.shared.restaurant.*
import java.time.LocalDate

class EmployeeDetailsViewModel @AssistedInject constructor(
    private val employeeClient: EmployeeClient,
    private val reservationClient: ReservationClient,
    @Assisted private var employeeId: Long
) : ViewModel() {

    private val _employee: MutableLiveData<EmployeeInfo> = MutableLiveData()
    val employee: LiveData<EmployeeInfo> = _employee

    private val _fetchedReservations: MutableLiveData<List<ReservationInfo>> = MutableLiveData()
    private val _filteredReservations: MutableLiveData<List<ReservationInfo>> = MutableLiveData()
    val reservations: LiveData<List<ReservationInfo>> = _filteredReservations

    private val _date: MutableLiveData<LocalDate> = MutableLiveData(LocalDate.now())
    val date: LiveData<LocalDate> = _date

    private val _search: MutableLiveData<String> = MutableLiveData("")
    val search: LiveData<String> = _search

    private val _showAll: MutableLiveData<Boolean> = MutableLiveData(false)
    val showAll: LiveData<Boolean> = _showAll

    init {
        viewModelScope.launch {
            _employee.value = employeeClient.getById(employeeId)
            Log.i(javaClass.name, _employee.value.toString())
            fetchReservations()
        }
    }

    fun search(keyword: String) {
        _search.value = keyword
        filterReservations()
    }

    fun setSearchDate(date: LocalDate) {
        _date.value = date
        viewModelScope.launch {
            fetchReservations()
        }
    }

    fun setShowAll(boolean: Boolean) {
        _showAll.value = boolean
        filterReservations()
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
        }
    }

    private suspend fun fetchReservations() {
        try {
            _fetchedReservations.value = reservationClient.getReservationsOfRestaurant(
                employee.value!!.restaurant.id,
                date.value!!.toKotlinLocalDate()
            )
            filterReservations()
        } catch (e: ClientException) {
            Log.w(javaClass.name, "failed to fetch reservations", e)
        }
    }

    private fun filterReservations() {
        val showAll = showAll.value!!
        _filteredReservations.value = _fetchedReservations.value!!
            .filter { reservation -> showAll || reservation.status == ReservationStatus.PENDING }
            .filter { reservation -> reservation.toString().contains(search.value!!) }
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

