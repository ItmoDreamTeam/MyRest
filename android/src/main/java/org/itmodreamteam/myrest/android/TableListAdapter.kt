package org.itmodreamteam.myrest.android

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.itmodreamteam.myrest.android.databinding.FragmentTableBinding
import org.itmodreamteam.myrest.shared.table.TableView

class TableListAdapter(private val viewModel : RestaurantDetailsViewModel) : ListAdapter<TableView, TableListAdapter.TableViewHolder>(TableViewCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableListAdapter.TableViewHolder {
        val binding = FragmentTableBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TableViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TableListAdapter.TableViewHolder, position: Int) {
        val table = getItem(position)
        holder.bind(table)
    }

    inner class TableViewHolder(private val binding: FragmentTableBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.table?.let { table ->
                    viewModel.setReservationTable(table)
                    it.findNavController().navigateUp()
                }
            }
        }

        fun bind(item: TableView) {
            binding.apply {
                table = item
                executePendingBindings()
            }
        }
    }
}

object TableViewCallback : DiffUtil.ItemCallback<TableView>() {
    override fun areItemsTheSame(oldItem: TableView, newItem: TableView): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TableView, newItem: TableView): Boolean {
        return oldItem == newItem
    }
}