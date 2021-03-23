package server.authentication.mongo.exceptions

open class AuthenticationException : Exception {
    constructor() : super()
    constructor(message: String) : super(message)
}