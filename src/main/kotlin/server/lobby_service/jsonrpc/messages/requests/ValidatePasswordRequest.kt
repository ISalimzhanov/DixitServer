package server.lobby_service.jsonrpc.messages.requests

import com.fasterxml.jackson.annotation.JsonTypeName
import server.api.jsonrpc.JsonRpcRequest

@JsonTypeName("validatePassword")
class ValidatePasswordRequest(
    params: Params,
    id: String,
) : JsonRpcRequest<ValidatePasswordRequest.Params>(
    "validatePassword", params, id
) {
    data class Params(
        val lobbyId: String,
        val password: String,
    ) : JsonRpcRequest.Params()
}