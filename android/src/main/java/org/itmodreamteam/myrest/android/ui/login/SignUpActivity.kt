package org.itmodreamteam.myrest.android.ui.login

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import org.itmodreamteam.myrest.android.R

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val phone = findViewById<EditText>(R.id.signUpPhone)
        val firstName = findViewById<EditText>(R.id.signUpFirstName)
        val lastName = findViewById<EditText>(R.id.signUpLastName)
        val submit = findViewById<Button>(R.id.signUpSubmit)
        val back = findViewById<Button>(R.id.signUpBack)

        phone.setText(intent.getStringExtra(EXTRA_PHONE))
        phone.isEnabled = false

        // TODO replace with navigator
        back.setOnClickListener {
            finish()
        }
    }
}