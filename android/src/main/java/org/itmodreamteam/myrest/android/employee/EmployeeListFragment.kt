package org.itmodreamteam.myrest.android.employee

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.itmodreamteam.myrest.android.databinding.FragmentEmployeeListBinding

@AndroidEntryPoint
class EmployeeListFragment: Fragment() {
    private val model: EmployeeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEmployeeListBinding.inflate(inflater, container, false)
        val adapter = EmployeeListAdapter()
        binding.list.adapter = adapter
        model.employees.observe(viewLifecycleOwner) { employees ->
            adapter.submitList(employees)
        }

        return binding.root
    }
}