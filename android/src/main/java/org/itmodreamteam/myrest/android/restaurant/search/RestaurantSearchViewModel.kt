package org.itmodreamteam.myrest.android.restaurant.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.itmodreamteam.myrest.shared.Pageable
import org.itmodreamteam.myrest.shared.restaurant.RestaurantController
import org.itmodreamteam.myrest.shared.restaurant.RestaurantControllerImpl

class RestaurantSearchViewModel(
    private val restaurantController: RestaurantController = RestaurantControllerImpl(),
) : ViewModel() {

    fun search(keyword: String, pageable: Pageable) = viewModelScope.launch {
        Log.i(javaClass.name, "Searching restaurants by keyword '$keyword'")
        val resultsPage = restaurantController.search(keyword, pageable)
        Log.i(javaClass.name, "Results page: $resultsPage")
    }
}
