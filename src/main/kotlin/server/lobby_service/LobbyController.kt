package server.lobby_service

import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.*
import server.ExceptionService
import server.api.jsonrpc.JsonRpcRequest
import server.api.jsonrpc.JsonRpcResponse
import server.api.jsonrpc.messages.response.InvalidMethodResponse
import server.lobby_service.jsonrpc.messages.requests.*
import server.lobby_service.mongo.services.LobbyHandler

@RestController
@CrossOrigin(origins = ["*"])
class LobbyController(
    private val handler: LobbyHandler,
    private val exceptionService: ExceptionService,
) {
    @RequestMapping("/api/lobbies", method = [RequestMethod.OPTIONS, RequestMethod.POST])
    fun handleRequest(@RequestBody request: JsonRpcRequest<*>): ResponseEntity<JsonRpcResponse> {
        val response: JsonRpcResponse = when (request) {
            is CreateLobbyRequest -> {
                handler.create(request)
            }
            is JoinLobbyRequest -> {
                handler.join(request)
            }
            is LeaveLobbyRequest -> {
                handler.leave(request)
            }
            is FindLobbyRequest -> {
                handler.find(request)
            }
            is ValidatePasswordRequest -> {
                handler.validatePassword(request)
            }
            is StartGameRequest -> {
                handler.start(request)
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