package org.itmodreamteam.myrest.android

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.itmodreamteam.myrest.android.ui.login.SignInOrSignUp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = Intent(this, SignInOrSignUp::class.java)
        startActivity(intent)
    }
}
