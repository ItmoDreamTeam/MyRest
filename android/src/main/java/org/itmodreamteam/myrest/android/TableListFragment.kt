package org.itmodreamteam.myrest.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import org.itmodreamteam.myrest.android.databinding.FragmentTableListBinding

class TableListFragment : Fragment() {

    private val viewModel: RestaurantDetailsViewModel by navGraphViewModels(R.id.nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTableListBinding.inflate(inflater, container, false)
        val adapter = TableListAdapter(viewModel)
        binding.list.adapter = adapter
        viewModel.tables.observe(viewLifecycleOwner) { tables ->
            adapter.submitList(tables)
        }
        return binding.root
    }
}