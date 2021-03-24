package server.lobby_service.jsonrpc.messages.requests

import com.fasterxml.jackson.annotation.JsonTypeName
import server.api.jsonrpc.JsonRpcRequest
import server.lobby_service.mongo.documents.lobbies.LobbyRegion

@JsonTypeName("join")
class JoinLobbyRequest(
    params: Params,
    id: String,
) : JsonRpcRequest<JoinLobbyRequest.Params>(
    "join",
    params,
    id
) {
    data class Params(
        val userId: String,
        val connector: String,
        val lobbyId: String,
        val password: String,
    ) : JsonRpcRequest.Params()
}