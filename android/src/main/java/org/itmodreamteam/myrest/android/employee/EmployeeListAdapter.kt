package org.itmodreamteam.myrest.android.employee

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.itmodreamteam.myrest.android.databinding.FragmentEmployeeBinding
import org.itmodreamteam.myrest.shared.restaurant.EmployeeInfo

class EmployeeListAdapter: ListAdapter<EmployeeInfo, EmployeeListAdapter.EmployeeViewHolder>(EmployeeDiffCallback){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val binding = FragmentEmployeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee = getItem(position)
        holder.bind(employee)
    }

    inner class EmployeeViewHolder(private val binding: FragmentEmployeeBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.employee?.let { employee ->
                    Log.i(javaClass.name, "navigate to reservations")
                    navigateToEmployee(employee, it)
                }
            }
        }

        fun bind(item: EmployeeInfo) {
            binding.apply {
                employee = item
                executePendingBindings()
            }
        }

        private fun navigateToEmployee(employeeInfo: EmployeeInfo, view: View) {
            val direction =
                EmployeeListFragmentDirections.actionEmployeeListFragmentToEmployeeDetailsFragment(
                    employeeInfo.id
                )
            view.findNavController().navigate(direction)
        }
    }
}

object EmployeeDiffCallback : DiffUtil.ItemCallback<EmployeeInfo>() {
    override fun areItemsTheSame(oldItem: EmployeeInfo, newItem: EmployeeInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: EmployeeInfo, newItem: EmployeeInfo): Boolean {
        return oldItem == newItem
    }
}