package authentication.mongo.api

import authentication.mongo.api.jsonrpc.JsonRpcError
import authentication.mongo.api.jsonrpc.JsonRpcRequest
import authentication.mongo.api.jsonrpc.JsonRpcResponse
import authentication.mongo.users.User
import authentication.mongo.users.UserRepository
import authentication.mongo.users.exceptions.IncorrectPasswordException
import authentication.mongo.users.exceptions.UserDoesntExistException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin(origins = ["*"])
class UserController(
    @Autowired private val userRepository: UserRepository,
) {
    fun login(alias: String, password: String): User {
        val user: User = userRepository.findByAlias(alias)
            ?: throw UserDoesntExistException()
        if (user.password != password)
            throw IncorrectPasswordException()
        return user
    }

    fun register(alias: String, password: String): User {
        val user = User(alias, password)
        userRepository.save(user)
        return user
    }

    @RequestMapping("/api/users", method = [RequestMethod.OPTIONS, RequestMethod.POST])
    fun handleRequest(@RequestBody request: JsonRpcRequest): ResponseEntity<JsonRpcResponse> {
        var result: Any? = null
        var error: JsonRpcError? = null
        when (request.method) {
            "login" -> {
                if (request.params["alias"] == null || request.params["password"] == null) {
                    error = JsonRpcError(
                        code = -10,
                        message = "Alias, password or both are not defined"
                    )
                } else {
                    try {
                        result = login(
                            request.params["alias"]!! as String,
                            request.params["password"]!! as String,
                        )
                    } catch (e: UserDoesntExistException) {
                        error = JsonRpcError(
                            code = -11,
                            message = e.message!!
                        )
                    } catch (e: IncorrectPasswordException) {
                        error = JsonRpcError(
                            code = -12,
                            message = e.message!!
                        )
                    }
                }
            }
            "register" -> {
                if (request.params["alias"] == null || request.params["password"] == null) {
                    result = null
                    error = JsonRpcError(
                        code = -20,
                        message = "Alias, password or both are not defined"
                    )
                } else {
                    try {
                        result = register(
                            request.params["alias"]!! as String,
                            request.params["password"]!! as String,
                        )
                    } catch (e: DuplicateKeyException) {
                        error = JsonRpcError(
                            code = -21,
                            message = e.message!!
                        )
                    }
                }
            }
        }
        val response = JsonRpcResponse(result = result, error = error, id = request.id)
        return ResponseEntity.ok(response)
    }
}