package org.itmodreamteam.myrest.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.itmodreamteam.myrest.android.databinding.FragmentSettingsBinding

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private val model: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSettingsBinding.inflate(inflater, container, false)
        binding.settings = model
        binding.logout.setOnClickListener {
            model.logout()
            val action =
                SettingsFragmentDirections.actionSettingsFragmentToSetPhone()
            findNavController().navigate(action)
        }
        return binding.root
    }
}