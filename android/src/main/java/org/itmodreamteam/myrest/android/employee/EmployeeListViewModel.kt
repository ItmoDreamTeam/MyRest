package org.itmodreamteam.myrest.android.employee

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.itmodreamteam.myrest.shared.restaurant.EmployeeInfo
import org.itmodreamteam.myrest.shared.restaurant.RestaurantClient

class EmployeeListViewModel @ViewModelInject constructor(
    private val restaurantClient: RestaurantClient
) : ViewModel() {
    private val _employees = MutableLiveData<List<EmployeeInfo>>()
    val employees: LiveData<List<EmployeeInfo>> = _employees

    init {
        viewModelScope.launch {
            _employees.value = restaurantClient.getRestaurantsOfUser().filter { employee -> employee.active() }
        }
    }
}