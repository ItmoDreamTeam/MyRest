package org.itmodreamteam.myrest.server

import com.google.firebase.FirebaseApp
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class MyRestServer

fun main() {
    FirebaseApp.initializeApp()
    SpringApplication.run(MyRestServer::class.java)
}
