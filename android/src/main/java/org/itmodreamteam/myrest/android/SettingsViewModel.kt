package org.itmodreamteam.myrest.android

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import org.itmodreamteam.myrest.android.data.SignInRepository
import org.itmodreamteam.myrest.shared.user.Profile

class SettingsViewModel @ViewModelInject constructor(
    private val signInRepository: SignInRepository
) : ViewModel() {
    val signedInUser: LiveData<Profile> = signInRepository.signedInUser

    fun isEmployeeMode() = signInRepository.employeeMode.value

    fun employeeModeChanged(mode: Boolean) {
        signInRepository.employeeMode.value = mode
    }
}