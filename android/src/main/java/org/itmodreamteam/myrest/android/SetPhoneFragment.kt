package org.itmodreamteam.myrest.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.itmodreamteam.myrest.android.databinding.FragmentSetPhoneBinding

@AndroidEntryPoint
class SetPhone : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSetPhoneBinding.inflate(inflater, container, false)

        binding.buttonSignIn.isEnabled = false
        binding.buttonSignUp.isEnabled = false

        binding.editTextPhone.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE ->
                    binding.editTextPhone.text.toString().let {
                        if (it.startsWith("7") && it.length != 11) {
                            binding.editTextPhone.error = getString(R.string.invalid_phone)
                            binding.buttonSignIn.isEnabled = false
                            binding.buttonSignUp.isEnabled = false
                        } else {
                            binding.buttonSignIn.isEnabled = true
                            binding.buttonSignUp.isEnabled = true
                        }
                    }
            }
            false
        }
        binding.buttonSignIn.setOnClickListener {
            val action =
                SetPhoneDirections.actionSetPhoneToSignIn(binding.editTextPhone.text.toString())
            findNavController().navigate(action)
        }
        return binding.root
    }
}