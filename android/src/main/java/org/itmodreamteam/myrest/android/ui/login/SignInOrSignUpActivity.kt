package org.itmodreamteam.myrest.android.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import org.itmodreamteam.myrest.android.MyRestApplication
import org.itmodreamteam.myrest.android.R
import org.itmodreamteam.myrest.android.data.SignInRepository


const val EXTRA_PHONE = "org.itmodreamteam.myrest.android.ui.login.PHONE"

class SignInOrSignUp : AppCompatActivity() {
    private lateinit var signInRepository: SignInRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_or_sign_up)

        val phone = findViewById<EditText>(R.id.editTextPhone)
        val signIn = findViewById<Button>(R.id.buttonSignIn)
        val signUp = findViewById<Button>(R.id.buttonSignUp)

        signInRepository = (application as MyRestApplication).signInRepository

        // TODO view model for phone + validation

        signIn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            intent.putExtra(EXTRA_PHONE, phone.text.toString())
            // TODO send a message with verification code
            startActivity(intent)
        }

        signUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            intent.putExtra(EXTRA_PHONE, phone.text.toString())
            startActivity(intent)
        }
    }
}