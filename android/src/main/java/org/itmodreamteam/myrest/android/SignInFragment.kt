package org.itmodreamteam.myrest.android

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import org.itmodreamteam.myrest.android.data.SignInRepository
import org.itmodreamteam.myrest.android.databinding.FragmentSignInBinding
import org.itmodreamteam.myrest.android.ui.afterTextChanged
import org.itmodreamteam.myrest.android.ui.login.SignInViewModel
import org.itmodreamteam.myrest.shared.error.ClientException
import org.itmodreamteam.myrest.shared.user.Profile

@AndroidEntryPoint
class SignInFragment : Fragment() {
    private val args: SignInFragmentArgs by navArgs()
//    private val model: SignInViewModel by navGraphViewModels(R.navigation.nav_graph)
    private val model: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSignInBinding.inflate(inflater, container, false)
        binding.phone = args.phone

        binding.toolbar.setNavigationOnClickListener { view ->
            view.findNavController().navigateUp()
        }

        model.signInFormState.observe(viewLifecycleOwner, Observer {
            val signInState = it ?: return@Observer
            binding.signInSubmit.isEnabled = signInState.isDataValid
            if (signInState.phoneError != null) {
                binding.signInPhone.error = getString(signInState.phoneError)
            }
            if (signInState.codeError != null) {
                binding.signInCode.error = getString(signInState.codeError)
            }
        })

        model.signInResult.observe(viewLifecycleOwner, Observer {
            val signInResult = it ?: return@Observer
           binding.signInprogressBar.visibility = View.GONE
            if (signInResult.error != null) {
                showLoginFailed(signInResult.error, signInResult.exception)
            }
            if (signInResult.success != null) {
                updateUiWithUser(signInResult.success)
                // TODO route to profile fragment
            }
        })

        binding.signInCode.afterTextChanged {
            model.signInDataChanged(
                binding.signInPhone.text.toString(),
                binding.signInCode.text.toString()
            )
        }

        binding.signInCode.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE ->
                    model.startSession(
                        binding.signInPhone.text.toString(),
                        binding.signInCode.text.toString()
                    )
            }
            false
        }

        binding.signInSubmit.setOnClickListener {
            binding.signInprogressBar.visibility = View.VISIBLE
            model.startSession(binding.signInPhone.text.toString(), binding.signInCode.text.toString())
        }

        binding.signInSend.setOnClickListener {
            model.signIn(binding.signInPhone.text.toString())
        }

        model.tryToRecoverSession()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(context, args.phone, Toast.LENGTH_LONG).show()

    }

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