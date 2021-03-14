package org.itmodreamteam.myrest.android

import androidx.lifecycle.*
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinLocalDateTime
import org.itmodreamteam.myrest.shared.restaurant.ReservationClient
import org.itmodreamteam.myrest.shared.restaurant.RestaurantClient
import org.itmodreamteam.myrest.shared.restaurant.RestaurantInfo
import org.itmodreamteam.myrest.shared.table.TableClient
import org.itmodreamteam.myrest.shared.table.TableView
import java.time.*

class RestaurantDetailsViewModel @AssistedInject constructor(
    private val restaurantClient: RestaurantClient,
    private val reservationClient: ReservationClient,
    private val tableClient: TableClient,
    @Assisted private val restaurantId: Long
) : ViewModel() {
    private val _restaurant: MutableLiveData<RestaurantInfo> = MutableLiveData()
    val restaurant: LiveData<RestaurantInfo> = _restaurant
    private val _tables: MutableLiveData<List<TableView>> = MutableLiveData()
    val tables: MutableLiveData<List<TableView>> = _tables
    private val _selectedTable: MutableLiveData<TableView> = MutableLiveData()
    val selectedTable: LiveData<TableView> = _selectedTable

    private val _reservationDate: MutableLiveData<LocalDate> = MutableLiveData(LocalDate.now())
    val reservationDate: LiveData<LocalDate> = _reservationDate

    private val _reservationTime: MutableLiveData<LocalTime> = MutableLiveData(LocalTime.now().plusHours(1))
    val reservationTime: LiveData<LocalTime> = _reservationTime

    // TODO
    private val _reservationDuration: MutableLiveData<Duration> =
        MutableLiveData(Duration.ofDays(2))

    init {
        viewModelScope.launch {
            _restaurant.value = restaurantClient.getById(restaurantId)
            _tables.value = tableClient.getRestaurantTables(restaurantId)
        }
    }

    fun setReservationDate(date: LocalDate) {
        _reservationDate.value = date
    }

    fun setReservationTime(time: LocalTime) {
        _reservationTime.value = time
    }

    fun setReservationTable(tableView: TableView) {
        _selectedTable.value = tableView
    }

    fun reserve() {
        // TODO disable button until all is valid
        viewModelScope.launch {
            val utc = ZoneId.of("UTC")
            val from = LocalDateTime.of(_reservationDate.value, _reservationTime.value)
            val until = LocalDateTime.of(_reservationDate.value, _reservationTime.value)
                .plus(_reservationDuration.value)
            reservationClient.submitReservationForApproval(
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