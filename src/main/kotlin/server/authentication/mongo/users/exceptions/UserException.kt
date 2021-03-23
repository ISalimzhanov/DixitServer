package server.authentication.mongo.users.exceptions

import server.authentication.mongo.AuthenticationException

open class UserException : AuthenticationException {
    constructor() : super()
    constructor(message: String) : super(message)
}