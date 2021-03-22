package authentication.mongo

open class AuthenticationException : Exception {
    constructor() : super()
    constructor(message: String) : super(message)
}