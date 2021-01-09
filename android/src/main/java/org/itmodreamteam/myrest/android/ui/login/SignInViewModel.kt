package org.itmodreamteam.myrest.android.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.itmodreamteam.myrest.android.R
import org.itmodreamteam.myrest.android.data.Result
import org.itmodreamteam.myrest.android.data.SignInRepository

class SignInViewModel(private val signInRepository: SignInRepository) : ViewModel() {
    private val _singInFormState = MutableLiveData<SignInFormState>()
    val signInFormState: LiveData<SignInFormState> = _singInFormState

    private val _signIn = MutableLiveData<SignInResult>()
    val signInResult: LiveData<SignInResult> = _signIn

    fun signIn(phone: String) {
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
            signInRepository.user
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