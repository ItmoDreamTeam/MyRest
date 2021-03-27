package org.itmodreamteam.myrest.android

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.itmodreamteam.myrest.android.databinding.FragmentSignInBinding
import org.itmodreamteam.myrest.android.ui.afterTextChanged
import org.itmodreamteam.myrest.android.ui.hideKeyboard
import org.itmodreamteam.myrest.android.ui.login.SignInViewModel
import org.itmodreamteam.myrest.shared.error.ClientException
import org.itmodreamteam.myrest.shared.user.Profile
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private val args: SignInFragmentArgs by navArgs()
    private val model: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSignInBinding.inflate(inflater, container, false)
        binding.phone = args.phone

        model.signInFormState.observe(viewLifecycleOwner, Observer {
            val signInState = it ?: return@Observer

            if (signInState.phoneError != null) {
                binding.signInPhone.error = getString(signInState.phoneError)
            }
            if (signInState.codeError != null) {
                binding.signInCode.error = getString(signInState.codeError)
            }
            if (signInState.isDataValid) {
                hideKeyboard(binding.signInCode)
                model.startSession(phone(), binding.signInCodeEdit.text.toString())
            }
        })

        model.signInResult.observe(viewLifecycleOwner, Observer {
            binding.progress.visibility = GONE
            val signInResult = it ?: return@Observer
            if (signInResult.error != null) {
                showLoginFailed(signInResult.error, signInResult.exception)
            }
            if (signInResult.success != null) {
                updateUiWithUser(signInResult.success)
                val action =
                    SignInFragmentDirections.actionSignInToReservationListFragment()
                findNavController().navigate(action)
            }
        })

        binding.signInCodeEdit.afterTextChanged {
            model.signInDataChanged(
                phone(),
                binding.signInCodeEdit.text.toString()
            )
        }

        binding.signInCodeEdit.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    binding.progress.visibility = VISIBLE
                    model.startSession(phone(), binding.signInCodeEdit.text.toString())
                }
            }
            false
        }


        binding.signInSendAgain.setOnClickListener {
            binding.signInSendAgain.isEnabled = false
            model.signIn(phone())
            countDown(binding)
        }

        model.tryToRecoverSession()
        countDown(binding)
        return binding.root
    }

    private fun phone() = "7${args.phone}"

    private fun countDown(binding: FragmentSignInBinding) {
        object: CountDownTimer(TimeUnit.SECONDS.toMillis(20), TimeUnit.SECONDS.toMillis(1)) {
            override fun onTick(millisUntilFinished: Long) {
                binding.signInSendAgain.text = "Повторно запросить код через ${TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)}"
            }

            override fun onFinish() {
                binding.signInSendAgain.text = "Повторно запросить код"
                binding.signInSendAgain.isEnabled = true
            }
        }.start()
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        Toast.makeText(context, args.phone, Toast.LENGTH_LONG).show()
//
//    }

    private fun updateUiWithUser(model: Profile) {
        val welcome = getString(R.string.welcome)
        val displayName = model.firstName + " " + model.lastName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            context,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int, exception: ClientException?) {
        // TODO show client exception in a toast?
        Toast.makeText(context, errorString, Toast.LENGTH_SHORT).show()
        Log.w(javaClass.name, exception)
    }
}