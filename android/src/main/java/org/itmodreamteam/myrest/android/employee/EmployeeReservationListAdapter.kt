package org.itmodreamteam.myrest.android.employee

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.itmodreamteam.myrest.android.databinding.FragmentEmployeeReservationBinding
import org.itmodreamteam.myrest.shared.restaurant.ReservationInfo
import org.itmodreamteam.myrest.shared.restaurant.ReservationStatus

class EmployeeReservationListAdapter(private val model: EmployeeDetailsViewModel,
                                     private val clickListener: ClickListener
): ListAdapter<ReservationInfo, EmployeeReservationListAdapter.ReservationViewHolder>(ReservationDiffCallback){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val binding = FragmentEmployeeReservationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val restaurant = getItem(position)
        holder.bind(restaurant)
    }

    inner class ReservationViewHolder(private val binding: FragmentEmployeeReservationBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                if (binding.reservation!!.status ==  ReservationStatus.PENDING) {
                    clickListener.onClick(binding.reservation!!)
                }
            }
        }

        fun bind(item: ReservationInfo) {
            binding.apply {
                reservation = item
                executePendingBindings()
            }
        }
    }
}

interface ClickListener {
    fun onClick(reservation: ReservationInfo)
}

object ReservationDiffCallback : DiffUtil.ItemCallback<ReservationInfo>() {
    override fun areItemsTheSame(oldItem: ReservationInfo, newItem: ReservationInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ReservationInfo, newItem: ReservationInfo): Boolean {
        return oldItem == newItem
    }
}