package org.itmodreamteam.myrest.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.itmodreamteam.myrest.android.databinding.FragmentRestaurantListBinding

@AndroidEntryPoint
class RestaurantListFragment : Fragment() {
    private val model: RestaurantSearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRestaurantListBinding.inflate(inflater, container, false)
        val adapter = RestaurantListAdapter()
        binding.list.adapter = adapter
        subscribeUi(adapter)
        model.search("")
        return binding.root
    }

    private fun subscribeUi(adapter: RestaurantListAdapter) {
        model.restaurants.observe(viewLifecycleOwner) { restaurants ->
            adapter.submitList(restaurants)
        }
    }
}