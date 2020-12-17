package org.itmodreamteam.myrest.server.error

class UserException(vararg val errorsKeys: String) : RuntimeException()
