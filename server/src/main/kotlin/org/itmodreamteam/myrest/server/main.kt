package org.itmodreamteam.myrest.server

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class MyRestServer

fun main() {
    SpringApplication.run(MyRestServer::class.java)
}
