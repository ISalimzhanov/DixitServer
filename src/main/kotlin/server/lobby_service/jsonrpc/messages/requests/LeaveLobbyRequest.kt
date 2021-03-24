package server.lobby_service.jsonrpc.messages.requests

import com.fasterxml.jackson.annotation.JsonTypeName
import server.api.jsonrpc.JsonRpcRequest
import server.api.jsonrpc.JsonRpcResponse

@JsonTypeName("leave")
class LeaveLobbyRequest(
    params: Params,
    id: String,
) : JsonRpcRequest<LeaveLobbyRequest.Params>("leave", params, id) {
    data class Params(
        val userId: String,
        val lobbyId: String,
    ) : JsonRpcRequest.Params()
}