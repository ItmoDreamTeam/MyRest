package org.itmodreamteam.myrest.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.itmodreamteam.myrest.android.data.Result
import org.itmodreamteam.myrest.android.databinding.FragmentSettingsBinding
import org.itmodreamteam.myrest.android.ui.afterTextChanged
import org.itmodreamteam.myrest.android.ui.showFail

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private val model: SettingsViewModel by viewModels()
    private val args: SettingsFragmentArgs by navArgs()

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
        val signedInUser = model.signedInUser.value!!
        binding.firstNameEdit.setText(signedInUser.firstName)
        binding.lastNameEdit.setText(signedInUser.lastName)
        binding.discard.setOnClickListener {
            model.profileDataCleaned()
            binding.firstNameEdit.setText(signedInUser.firstName)
            binding.lastNameEdit.setText(signedInUser.lastName)
            model.viewMode()
        }

        model.currentProfileModeIsEdit.observe(viewLifecycleOwner) { isEdit ->
            if (isEdit) {
                binding.firstName.isEnabled = true
                binding.lastName.isEnabled = true
                binding.save.text = "Сохранить"
                binding.discard.visibility = VISIBLE
            } else {
                binding.firstName.isEnabled = false
                binding.lastName.isEnabled = false
                binding.save.text = "Изменить"
                binding.discard.visibility = INVISIBLE
            }
        }

        if (args.editProfile) {
            model.editMode()
        } else {
            model.viewMode()
        }


        binding.firstNameEdit.afterTextChanged {
            model.profileDataChanged(binding.firstNameEdit.text.toString(), binding.lastNameEdit.text.toString())
        }

        binding.lastNameEdit.afterTextChanged {
            model.profileDataChanged(binding.firstNameEdit.text.toString(), binding.lastNameEdit.text.toString())
        }

        model.saveResult.observe(viewLifecycleOwner) { result ->
            if (result is Result.Error) {
                showFail(result.exception)
            } else {
                Toast.makeText(context, "Данные успешно обновлены", LENGTH_LONG).show()
            }
        }

        return binding.root
    }
}