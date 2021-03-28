package org.itmodreamteam.myrest.android

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.itmodreamteam.myrest.android.data.Result
import org.itmodreamteam.myrest.android.data.SignInRepository
import org.itmodreamteam.myrest.shared.user.Profile

class SettingsViewModel @ViewModelInject constructor(
    private val signInRepository: SignInRepository
) : ViewModel() {
    val signedInUser: LiveData<Profile> = signInRepository.signedInUser

    private val _firstName: MutableLiveData<String> = MutableLiveData()
    val firstName: LiveData<String> = _firstName

    private val _lastName: MutableLiveData<String> = MutableLiveData()
    val lastName: LiveData<String> = _lastName

    private val _saveResult: MutableLiveData<Result<Profile>> = MutableLiveData()
    val saveResult: MutableLiveData<Result<Profile>> = _saveResult

    private val _currentProfileModeIsEdit: MutableLiveData<Boolean> = MutableLiveData(false)
    val currentProfileModeIsEdit: LiveData<Boolean> = _currentProfileModeIsEdit

    fun profileDataChanged(firstName: String, lastName: String) {
        _firstName.value = firstName
        _lastName.value = lastName
    }

    fun profileDataCleaned() {
        _firstName.value = null
        _lastName.value = null
    }

    fun editMode() {
        _currentProfileModeIsEdit.value = true
    }

    fun viewMode() {
        _currentProfileModeIsEdit.value = false
    }

    fun saveOrEdit() {
        val currentModeIsEdit = _currentProfileModeIsEdit.value!!
        val firstName = firstName.value
        val lastName = lastName.value

        Log.i(javaClass.name,"First name: $firstName, Last Name: $lastName")

        if (currentModeIsEdit) {
            if (firstName == null || lastName == null) {
                return
            }
            viewModelScope.launch {
                val result = signInRepository.updateProfile(firstName, lastName)
                _saveResult.value = result
                if (result is Result.Success) {
                    _currentProfileModeIsEdit.value = false
                }
            }
        } else {
            _currentProfileModeIsEdit.value = true
        }
    }

    fun isEmployeeMode() = signInRepository.employeeMode.value

    fun employeeModeChanged(mode: Boolean) {
        signInRepository.employeeMode.value = mode
    }

    fun logout() {
        signInRepository.logout()
    }
}