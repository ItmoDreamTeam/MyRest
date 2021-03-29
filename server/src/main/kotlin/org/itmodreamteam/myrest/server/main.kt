package org.itmodreamteam.myrest.server

import com.google.firebase.FirebaseApp
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.io.File

@SpringBootApplication
open class MyRestServer

fun main() {
    initializeFirebase()
    SpringApplication.run(MyRestServer::class.java)
}

fun initializeFirebase() {
    val credentialsPath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS")
    if (credentialsPath != null && File(credentialsPath).exists()) {
        FirebaseApp.initializeApp()
    }
}
