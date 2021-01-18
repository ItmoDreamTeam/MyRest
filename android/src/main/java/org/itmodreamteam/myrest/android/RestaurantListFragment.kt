package org.itmodreamteam.myrest.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
//        val view = inflater.inflate(R.layout.fragment_restaurant_list, container, false)

        val binding = FragmentRestaurantListBinding.inflate(inflater, container, false)
        val adapter = RestaurantListAdapter()
        binding.list.adapter = adapter
        // Set the adapter
//        if (view is RecyclerView) {
//            with(view) {
//                layoutManager = LinearLayoutManager(context)
//                adapter = RestaurantListAdapter()
//            }
//        }
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