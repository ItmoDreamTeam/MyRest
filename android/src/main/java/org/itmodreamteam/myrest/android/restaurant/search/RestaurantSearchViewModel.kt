package org.itmodreamteam.myrest.android.restaurant.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.itmodreamteam.myrest.shared.Pageable
import org.itmodreamteam.myrest.shared.restaurant.RestaurantClient
import org.itmodreamteam.myrest.shared.restaurant.RestaurantClientImpl

class RestaurantSearchViewModel(
    private val restaurantClient: RestaurantClient = RestaurantClientImpl(),
) : ViewModel() {

    fun search(keyword: String, pageable: Pageable) = viewModelScope.launch {
        Log.i(javaClass.name, "Searching restaurants by keyword '$keyword'")
        val resultsPage = restaurantClient.search(keyword, pageable)
        Log.i(javaClass.name, "Results page: $resultsPage")
    }
}
