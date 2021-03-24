package server.authentication.exceptions

open class AuthenticationException : Exception {
    constructor() : super()
    constructor(message: String) : super(message)
}