package server.authentication.mongo.exceptions.users

import server.authentication.mongo.exceptions.AuthenticationException

open class UserException : AuthenticationException {
    constructor() : super()
    constructor(message: String) : super(message)
}