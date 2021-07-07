package org.itmodreamteam.myrest.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.itmodreamteam.myrest.android.databinding.FragmentReservationBinding
import org.itmodreamteam.myrest.shared.restaurant.ReservationInfo
import org.itmodreamteam.myrest.shared.restaurant.RestaurantInfo

class ReservationListAdapter: ListAdapter<ReservationInfo, ReservationListAdapter.ReservationViewHolder>(ReservationDiffCallback){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val binding = FragmentReservationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val restaurant = getItem(position)
        holder.bind(restaurant)
    }

    inner class ReservationViewHolder(private val binding: FragmentReservationBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.reservation?.let { reservation ->
                    navigateToRestaurant(reservation.table.restaurant, it)
                }
            }
        }

        fun bind(item: ReservationInfo) {
            binding.apply {
                reservation = item
                executePendingBindings()
            }
        }

        private fun navigateToRestaurant(restaurantInfo: RestaurantInfo, view: View) {
            val direction =
                ReservationListFragmentDirections.actionReservationListFragmentToRestaurantDetailsFragment(
                    restaurantInfo.id
                )
            view.findNavController().navigate(direction)
        }
    }
}

object ReservationDiffCallback : DiffUtil.ItemCallback<ReservationInfo>() {
    override fun areItemsTheSame(oldItem: ReservationInfo, newItem: ReservationInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ReservationInfo, newItem: ReservationInfo): Boolean {
        return oldItem == newItem
    }
}