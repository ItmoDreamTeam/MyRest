package org.itmodreamteam.myrest.android

import android.util.Log
import androidx.lifecycle.*
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinLocalDateTime
import org.itmodreamteam.myrest.shared.restaurant.ReservationClient
import org.itmodreamteam.myrest.shared.restaurant.ReservationInfo
import org.itmodreamteam.myrest.shared.restaurant.RestaurantClient
import org.itmodreamteam.myrest.shared.restaurant.RestaurantInfo
import org.itmodreamteam.myrest.shared.table.TableClient
import org.itmodreamteam.myrest.shared.table.TableView
import java.time.*
import java.time.temporal.ChronoUnit

class RestaurantDetailsViewModel @AssistedInject constructor(
    private val restaurantClient: RestaurantClient,
    private val reservationClient: ReservationClient,
    private val tableClient: TableClient,
    @Assisted private var restaurantId: Long
) : ViewModel() {
    private val _restaurant: MutableLiveData<RestaurantInfo> = MutableLiveData()
    val restaurant: LiveData<RestaurantInfo> = _restaurant
    private val _tables: MutableLiveData<List<TableView>> = MutableLiveData()
    val tables: MutableLiveData<List<TableView>> = _tables
    private val _selectedTable: MutableLiveData<TableView> = MutableLiveData()
    val selectedTable: LiveData<TableView> = _selectedTable

    private val _reservationDate: MutableLiveData<LocalDate> = MutableLiveData(LocalDate.now())
    val reservationDate: LiveData<LocalDate> = _reservationDate

    private val _reservationTime: MutableLiveData<LocalTime> = MutableLiveData(LocalTime.now()
        .plusHours(1)
        .truncatedTo(ChronoUnit.HOURS))
    val reservationTime: LiveData<LocalTime> = _reservationTime

    // TODO
    private val _reservationDuration: MutableLiveData<Duration> =
        MutableLiveData(Duration.ofHours(2))

    private val _dataValid: MutableLiveData<Boolean> = MutableLiveData(false)
    val dataValid: LiveData<Boolean> = _dataValid

    private val _reservation: MutableLiveData<ReservationInfo> = MutableLiveData()
    val reservation: LiveData<ReservationInfo> = _reservation


    init {
        Log.i(javaClass.name,"Creating view model with restaurant id: $restaurantId")
        viewModelScope.launch {
            _restaurant.value = restaurantClient.getById(restaurantId)
            _tables.value = tableClient.getRestaurantTables(restaurantId)
        }
    }

    fun reset(restaurantId: Long) {
        _reservation.value = null
        if (this.restaurantId == restaurantId) {
            return
        }
        this.restaurantId = restaurantId
        viewModelScope.launch {
            _restaurant.value = restaurantClient.getById(restaurantId)
            _tables.value = tableClient.getRestaurantTables(restaurantId)
        }
        checkData()
    }

    fun setReservationDate(date: LocalDate) {
        _reservationDate.value = date
        checkData()
    }

    fun setReservationTime(time: LocalTime) {
        _reservationTime.value = time
        checkData()
    }

    fun setReservationTable(tableView: TableView) {
        _selectedTable.value = tableView
        checkData()
    }

    private fun checkData() {
        _dataValid.value = _selectedTable.value != null && _reservationDate.value != null && _reservationTime.value != null
    }

    fun reserve() {
        viewModelScope.launch {
            val utc = ZoneId.of("UTC")
            val from = LocalDateTime.of(_reservationDate.value, _reservationTime.value)
            val until = LocalDateTime.of(_reservationDate.value, _reservationTime.value)
                .plus(_reservationDuration.value)
            _reservation.value = reservationClient.submitReservationForApproval(
                _selectedTable.value!!.id,
                from.toKotlinLocalDateTime(),
                until.toKotlinLocalDateTime()
            )
        }
    }

    @AssistedInject.Factory
    interface AssistedFactory {
        fun create(restaurantId: Long): RestaurantDetailsViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            restaurantId: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(restaurantId) as T
            }
        }
    }
}