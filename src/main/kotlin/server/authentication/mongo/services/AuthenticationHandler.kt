package server.authentication.mongo.services

import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service
import server.api.jsonrpc.JsonRpcError
import server.authentication.exceptions.user.IncorrectPasswordException
import server.authentication.exceptions.user.UserDoesntExistException
import server.authentication.messages.requests.LoginPlayerRequest
import server.authentication.messages.requests.RegisterPlayerRequest
import server.authentication.messages.responses.LoginPlayerResponse
import server.authentication.messages.responses.RegisterPlayerResponse

@Service
class AuthenticationHandler(
    val userService: UserService
) {
    fun login(request: LoginPlayerRequest): LoginPlayerResponse {
        return try {
            LoginPlayerResponse(
                userService.login(
                    request.params.alias,
                    request.params.password,
                ), null, request.id
            )
        } catch (e: UserDoesntExistException) {
            LoginPlayerResponse(null, JsonRpcError(-10, e.message!!), request.id)
        } catch (e: IncorrectPasswordException) {
            LoginPlayerResponse(null, JsonRpcError(-11, e.message!!), request.id)
        }
    }

    fun register(request: RegisterPlayerRequest): RegisterPlayerResponse {
        return try {
            RegisterPlayerResponse(
                userService.register(
                    request.params.alias,
                    request.params.password,
                ), null, request.id
            )
        } catch (e: DuplicateKeyException) {
            RegisterPlayerResponse(null, JsonRpcError(-12, e.message!!), request.id)
        }
    }
}