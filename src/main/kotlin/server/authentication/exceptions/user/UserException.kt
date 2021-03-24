package server.authentication.exceptions.user

import server.authentication.exceptions.AuthenticationException

open class UserException : AuthenticationException {
    constructor() : super()
    constructor(message: String) : super(message)
}