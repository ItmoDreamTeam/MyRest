package org.itmodreamteam.myrest.android

import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
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
import org.itmodreamteam.myrest.android.ui.showFail
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

        val phone = phone()
        if (model.canSignIn()) {
            model.signIn(phone)
            Toast.makeText(context, "SMS на номер $phone отправлено", LENGTH_LONG).show()
        }
        countDown(binding)

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
                showFail(signInResult.error, signInResult.exception)
            }
            if (signInResult.success != null) {
                updateUiWithUser(signInResult.success)

                if (signInResult.success.firstName.isBlank() || signInResult.success.lastName.isBlank()) {
                    Toast.makeText(context, "Пожалуйста, укажите Ваше имя", LENGTH_LONG).show()
                    val action = SignInFragmentDirections.editProfile(true)
                    findNavController().navigate(action)
                } else {
                    val action =
                        SignInFragmentDirections.actionSignInToReservationListFragment()
                    findNavController().navigate(action)
                }
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

        countDown(binding)
        return binding.root
    }

    private fun phone() = "7${args.phone}"

    private fun countDown(binding: FragmentSignInBinding) {
        object: CountDownTimer(model.nextAllowedSignInAt.value!! - SystemClock.elapsedRealtime(), TimeUnit.SECONDS.toMillis(1)) {
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
}