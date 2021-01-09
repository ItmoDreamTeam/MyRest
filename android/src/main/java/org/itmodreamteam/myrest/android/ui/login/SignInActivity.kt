package org.itmodreamteam.myrest.android.ui.login

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import org.itmodreamteam.myrest.android.MyRestApplication
import org.itmodreamteam.myrest.android.R
import org.itmodreamteam.myrest.android.data.SignInRepository
import org.itmodreamteam.myrest.android.ui.afterTextChanged
import org.itmodreamteam.myrest.shared.error.ClientException
import org.itmodreamteam.myrest.shared.user.Profile

class SignInActivity : AppCompatActivity() {
    private lateinit var signInRepository: SignInRepository
    private lateinit var signInViewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        signInRepository = (application as MyRestApplication).signInRepository
        signInViewModel = SignInViewModel(signInRepository)

        val phone = findViewById<EditText>(R.id.signInPhone)
        val code = findViewById<EditText>(R.id.signInCode)
        val signIn = findViewById<Button>(R.id.signInSubmit)
        val back = findViewById<Button>(R.id.signInBack)
        val send = findViewById<Button>(R.id.signInSend)
        val progress = findViewById<ProgressBar>(R.id.signInprogressBar)

        // Disable editing phone text field
        phone.setText(intent.getStringExtra(EXTRA_PHONE))
        phone.isEnabled = false

        // TODO replace with navigator
        back.setOnClickListener {
            finish()
        }

        signInViewModel.signInFormState.observe(this@SignInActivity, Observer {
            val signInState = it ?: return@Observer
            signIn.isEnabled = signInState.isDataValid
            if (signInState.phoneError != null) {
                phone.error = getString(signInState.phoneError)
            }
            if (signInState.codeError != null) {
                code.error = getString(signInState.codeError)
            }
        })

        signInViewModel.signInResult.observe(this@SignInActivity, Observer {
            val signInResult = it ?: return@Observer
            progress.visibility = View.GONE
            if (signInResult.error != null) {
                showLoginFailed(signInResult.error, signInResult.exception)
            }
            if (signInResult.success != null) {
                updateUiWithUser(signInResult.success)
                setResult(Activity.RESULT_OK)
                finish()
            }
        })

        code.afterTextChanged {
            signInViewModel.signInDataChanged(
                phone.text.toString(),
                code.text.toString()
            )
        }

        code.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE ->
                    signInViewModel.startSession(
                        phone.text.toString(),
                        code.text.toString()
                    )
            }
            false
        }

        signIn.setOnClickListener {
            progress.visibility = View.VISIBLE
            signInViewModel.startSession(phone.text.toString(), code.text.toString())
        }

        send.setOnClickListener {
            signInViewModel.signIn(phone.text.toString())
        }

        // TODO timer for sign in
    }

    private fun updateUiWithUser(model: Profile) {
        val welcome = getString(R.string.welcome)
        val displayName = model.firstName + " " + model.lastName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun showLoginFailed(@StringRes errorString: Int, exception: ClientException?) {
        // TODO show client exception in a toast?
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
        Log.w(javaClass.name, exception)
    }
}