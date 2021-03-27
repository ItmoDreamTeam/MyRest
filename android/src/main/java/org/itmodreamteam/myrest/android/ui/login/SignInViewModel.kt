package org.itmodreamteam.myrest.android.ui.login

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.itmodreamteam.myrest.android.R
import org.itmodreamteam.myrest.android.data.Result
import org.itmodreamteam.myrest.android.data.SignInRepository


class SignInViewModel @ViewModelInject constructor(
    private val signInRepository: SignInRepository) : ViewModel() {
    private val _singInFormState = MutableLiveData<SignInFormState>()
    val signInFormState: LiveData<SignInFormState> = _singInFormState

    private val _signIn = MutableLiveData<SignInResult>()
    val signInResult: LiveData<SignInResult> = _signIn

    fun tryToRecoverSession() {
        Log.i(javaClass.name, "Try to recover session")
        viewModelScope.launch {
            val profile = signInRepository.tryToRecoverSession()
            if (profile != null && profile is Result.Success) {
                _signIn.value = SignInResult(success = profile.data)
            }
        }
    }

    fun logout() {
        signInRepository.logout()
    }

    fun getLastUsedPhone() : String? = signInRepository.getLastUsedPhone()

    fun signIn(phone: String) {
        Log.i(javaClass.name, "Phone: $phone")
        viewModelScope.launch {
            val signIn = signInRepository.signIn(phone)
            if (signIn is Result.Error) {
                _signIn.value =
                    SignInResult(error = R.string.login_failed, exception = signIn.exception)
            }
        }
    }

    fun startSession(phone: String, code: String) {
        viewModelScope.launch {
            val user = signInRepository.startSession(phone, code)
            if (user is Result.Success) {
                _signIn.value = SignInResult(success = user.data)
            } else if (user is Result.Error) {
                _signIn.value =
                    SignInResult(error = R.string.login_failed, exception = user.exception)
            }
        }
    }

    fun signInDataChanged(phone: String, code: String) {
        if (!isPhoneValid(phone)) {
            _singInFormState.value = SignInFormState(phoneError = R.string.invalid_phone)
        } else if (!isCodeValid(code)) {
            _singInFormState.value = SignInFormState(codeError = R.string.invalid_code)
        } else {
            _singInFormState.value = SignInFormState(isDataValid = true)
        }
        Log.i(javaClass.name, "$phone $code ${_singInFormState.value}")
    }

    private fun isPhoneValid(phone: String): Boolean {
        // TODO Russian only
        if (!phone.startsWith("7")) {
            return false
        }
        if (phone.length != 11) {
            return false
        }
        return true
    }

    private fun isCodeValid(code: String): Boolean {
        // TODO hardcode
        return code.length == 6
    }
}