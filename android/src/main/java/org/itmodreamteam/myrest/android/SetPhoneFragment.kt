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
import org.itmodreamteam.myrest.android.ui.hideKeyboard
import org.itmodreamteam.myrest.android.ui.login.SignInViewModel

@AndroidEntryPoint
class SetPhoneFragment : Fragment() {
    private val model: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSetPhoneBinding.inflate(inflater, container, false)

        binding.buttonSignIn.isEnabled = false

        // TODO duplicated checks, use ViewModel
        binding.phoneEdit.afterTextChanged {
            if (it.length != 10) {
                binding.buttonSignIn.isEnabled = false
            } else {
                binding.buttonSignIn.isEnabled = true
                binding.phoneEdit.error = null
                hideKeyboard(binding.phoneEdit)
            }
        }

//        binding.phoneEdit.setOnFocusChangeListener { view, hasFocus ->
//            if (!hasFocus) {
//                hideKeyboard(view)
//            }
//        }

        binding.phoneEdit.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE ->
                    binding.phoneEdit.text.toString().let {
                        if (it.length != 10) {
                            binding.phoneEdit.error = getString(R.string.invalid_phone)
                        }
                    }
            }
            false
        }
        binding.buttonSignIn.setOnClickListener {
            val action =
                SetPhoneFragmentDirections.actionSetPhoneToSignIn(binding.phoneEdit.text.toString())
            findNavController().navigate(action)
        }

        val lastUsedPhone = model.getLastUsedPhone()
        if (lastUsedPhone != null) {
            if (lastUsedPhone.startsWith("7") && lastUsedPhone.length == 11) {
                binding.phoneEdit.setText(lastUsedPhone.substring(1, lastUsedPhone.length))
            }
        }

        model.signInResult.observe(viewLifecycleOwner, Observer {
            val signInResult = it ?: return@Observer
            if (signInResult.success != null) {
                Log.i(javaClass.name, "Already signed in")
                if (signInResult.success.firstName.isBlank() && signInResult.success.lastName.isBlank()) {
                    val action = SetPhoneFragmentDirections.autoSessionEditProfile(true)
                    findNavController().navigate(action)
                } else {
                    val action = SetPhoneFragmentDirections.actionSetPhoneToRestaurantListFragment()
                    findNavController().navigate(action)
                }
            }
        })

        model.tryToRecoverSession()

        return binding.root
    }
}