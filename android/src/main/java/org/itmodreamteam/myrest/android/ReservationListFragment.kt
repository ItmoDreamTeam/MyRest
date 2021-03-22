package org.itmodreamteam.myrest.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.itmodreamteam.myrest.android.databinding.FragmentReservationListBinding

@AndroidEntryPoint
class ReservationListFragment : Fragment() {
    private val model: ReservationListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentReservationListBinding.inflate(inflater, container, false)
        val adapter = ReservationListAdapter()
        binding.list.adapter = adapter
        model.reservations.observe(viewLifecycleOwner) { reservations ->
            adapter.submitList(reservations)
        }
        return binding.root
    }
}