package org.itmodreamteam.myrest.android

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinLocalDate
import org.itmodreamteam.myrest.shared.restaurant.ReservationClient
import org.itmodreamteam.myrest.shared.restaurant.ReservationInfo
import java.time.LocalDate

class ReservationListViewModel @ViewModelInject constructor(
    private val reservationClient: ReservationClient
) : ViewModel() {
    private val _reservations = MutableLiveData<List<ReservationInfo>>()
    val reservations: LiveData<List<ReservationInfo>> = _reservations

    init {
        viewModelScope.launch {
            _reservations.value = reservationClient.getReservationsOfUser(LocalDate.now().toKotlinLocalDate())
        }
    }
}