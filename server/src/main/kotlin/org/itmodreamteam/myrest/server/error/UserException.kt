package org.itmodreamteam.myrest.server.error

class UserException(val interpolatableErrors: List<InterpolatableError>) : RuntimeException() {

    constructor(vararg errorsKeys: String) : this(errorsKeys.map { InterpolatableError(it) })

    constructor(key: String, parameter: Pair<String, Any>) : this(key, mapOf(parameter))

    constructor(key: String, parameters: Map<String, Any>) : this(listOf(InterpolatableError(key, parameters)))
}
