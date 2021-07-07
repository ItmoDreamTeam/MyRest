package org.itmodreamteam.myrest.android

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.itmodreamteam.myrest.shared.Pageable
import org.itmodreamteam.myrest.shared.restaurant.RestaurantClient
import org.itmodreamteam.myrest.shared.restaurant.RestaurantInfo

class RestaurantSearchViewModel @ViewModelInject constructor(
    private val restaurantClient: RestaurantClient
) : ViewModel() {
    private val _restaurants = MutableLiveData<List<RestaurantInfo>>()
    val restaurants: LiveData<List<RestaurantInfo>> = _restaurants

    fun search(keyword: String) {
        viewModelScope.launch {
            // TODO pageable
            val search = restaurantClient.search(keyword, Pageable(0, 10))
            _restaurants.value = search.content
        }
    }
}