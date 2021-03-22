package authentication.mongo.users.exceptions

import authentication.mongo.AuthenticationException

open class UserException : AuthenticationException {
    constructor() : super()
    constructor(message: String) : super(message)
}