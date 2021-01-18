package org.itmodreamteam.myrest.android

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import org.itmodreamteam.myrest.android.databinding.FragmentSetPhoneBinding
import org.itmodreamteam.myrest.android.ui.afterTextChanged
import org.itmodreamteam.myrest.android.ui.login.SignInViewModel

@AndroidEntryPoint
class SetPhone : Fragment() {
    private val model: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSetPhoneBinding.inflate(inflater, container, false)

        binding.buttonSignIn.isEnabled = false
        binding.buttonSignUp.isEnabled = false

        // TODO duplicated checks, use ViewModel
        binding.editTextPhone.afterTextChanged {
            if (it.startsWith("7") && it.length != 11) {
                binding.buttonSignIn.isEnabled = false
                binding.buttonSignUp.isEnabled = false
            } else {
                binding.buttonSignIn.isEnabled = true
                binding.buttonSignUp.isEnabled = true
                binding.editTextPhone.error = null
            }
        }

        binding.editTextPhone.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE ->
                    binding.editTextPhone.text.toString().let {
                        if (it.startsWith("7") && it.length != 11) {
                            binding.editTextPhone.error = getString(R.string.invalid_phone)
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

        val lastUsedPhone = model.getLastUsedPhone()
        if (lastUsedPhone != null) {
            binding.editTextPhone.setText(lastUsedPhone)
        }

        model.signInResult.observe(viewLifecycleOwner, Observer {
            val signInResult = it ?: return@Observer
            if (signInResult.success != null) {
                Log.i(javaClass.name, "Already signed in")
                val action = SetPhoneDirections.actionSetPhoneToRestaurantListFragment()
                findNavController().navigate(action)
            }
        })

        model.tryToRecoverSession()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
    }
}