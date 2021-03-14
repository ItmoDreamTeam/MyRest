package org.itmodreamteam.myrest.android

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import org.itmodreamteam.myrest.android.databinding.FragmentRestaurantBinding

import org.itmodreamteam.myrest.shared.restaurant.RestaurantInfo


class RestaurantListAdapter: ListAdapter<RestaurantInfo, RestaurantListAdapter.RestaurantViewHolder>(RestaurantDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding = FragmentRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.fragment_restaurant, parent, false)
        return RestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = getItem(position)
        holder.bind(restaurant)
    }

    inner class RestaurantViewHolder(private val binding: FragmentRestaurantBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.restarturant?.let { restaurant ->
                    navigateToRestaurant(restaurant, it)
                }
            }
        }

        fun bind(item: RestaurantInfo) {
            binding.apply {
                restarturant = item
                executePendingBindings()
            }
        }

        private fun navigateToRestaurant(restaurantInfo: RestaurantInfo, view: View) {
            val direction =
                RestaurantListFragmentDirections.actionRestaurantListFragmentToRestaurantDetailsFragment(
                    restaurantInfo.id
                )
            view.findNavController().navigate(direction)
        }
    }
}

object RestaurantDiffCallback : DiffUtil.ItemCallback<RestaurantInfo>() {
    override fun areItemsTheSame(oldItem: RestaurantInfo, newItem: RestaurantInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: RestaurantInfo, newItem: RestaurantInfo): Boolean {
        return oldItem == newItem
    }
}