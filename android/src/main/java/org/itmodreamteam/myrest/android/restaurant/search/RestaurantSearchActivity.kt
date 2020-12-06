package org.itmodreamteam.myrest.android.restaurant.search

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import org.itmodreamteam.myrest.android.R
import org.itmodreamteam.myrest.shared.Pageable

class RestaurantSearchActivity : AppCompatActivity() {

    private val viewModel: RestaurantSearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_search)

        val searchInput = findViewById<TextInputEditText>(R.id.rs_search_input)
        searchInput.addTextChangedListener {
            val keyword = it?.toString() ?: ""
            viewModel.search(keyword, Pageable(0, 20))
        }
    }
}
