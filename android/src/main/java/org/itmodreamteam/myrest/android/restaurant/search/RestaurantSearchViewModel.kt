package org.itmodreamteam.myrest.android.restaurant.search

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.itmodreamteam.myrest.android.AndroidErrorHandler
import org.itmodreamteam.myrest.shared.Pageable
import org.itmodreamteam.myrest.shared.error.ClientException
import org.itmodreamteam.myrest.shared.error.ErrorHandler
import org.itmodreamteam.myrest.shared.restaurant.RestaurantClient
import org.itmodreamteam.myrest.shared.restaurant.RestaurantClientImpl
import org.itmodreamteam.myrest.shared.restaurant.RestaurantStatus

class RestaurantSearchViewModel(
    private val restaurantClient: RestaurantClient = RestaurantClientImpl(),
    private val errorHandler: ErrorHandler<Context> = AndroidErrorHandler(),
) : ViewModel() {

    lateinit var context: Context

    fun search(keyword: String, pageable: Pageable) = viewModelScope.launch {
        Log.i(javaClass.name, "Searching by keyword $keyword")
        when (keyword) {
            "a" -> {
                try {
                    restaurantClient.updateStatusByAdmin(4, RestaurantStatus.BLOCKED)
                } catch (exception: ClientException) {
                    errorHandler.handle(context, exception)
                }
            }
            "e" -> {
                try {
                    restaurantClient.search(keyword, Pageable(0, 0))
                } catch (exception: ClientException) {
                    errorHandler.handle(context, exception)
                }
            }
            "s" -> {
                try {
                    restaurantClient.search(keyword, Pageable(0, 0))
                } catch (exception: ClientException) {
                    errorHandler.handle(context, exception) { errors ->
                        val message = "Custom error handling: ${errors.size} errors"
                        val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
                        toast.show()
                    }
                }
            }
            else -> {
                try {
                    val page = restaurantClient.search(keyword, pageable)
                    Log.i(javaClass.name, "Search result: ${page.content}")
                } catch (exception: ClientException) {
                    errorHandler.handle(context, exception)
                }
            }
        }
    }
}
