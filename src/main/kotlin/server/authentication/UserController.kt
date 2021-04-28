package server.authentication

import server.api.jsonrpc.JsonRpcRequest
import server.api.jsonrpc.JsonRpcResponse
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.*
import server.ExceptionService
import server.api.jsonrpc.messages.response.InvalidMethodResponse
import server.authentication.messages.requests.LoginPlayerRequest
import server.authentication.messages.requests.RegisterPlayerRequest
import server.authentication.mongo.services.AuthenticationHandler


@RestController
@CrossOrigin(origins = ["*"])
class UserController(
    private val authenticationService: AuthenticationHandler,
    private val exceptionService: ExceptionService,
) {
    @RequestMapping("/api/users", method = [RequestMethod.OPTIONS, RequestMethod.POST])
    fun handleRequest(@RequestBody request: JsonRpcRequest<*>): ResponseEntity<JsonRpcResponse> {
        val response: JsonRpcResponse = when (request) {
            is LoginPlayerRequest -> {
                authenticationService.login(request)
            }
            is RegisterPlayerRequest -> {
                authenticationService.register(request)
            }
            else -> {
                InvalidMethodResponse(request.id)
            }
        }
        return ResponseEntity.ok(response)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleInvalidRequest(
        exception: HttpMessageNotReadableException,
    ): JsonRpcResponse {
        return exceptionService.handleInvalidRequest(exception)
    }
}