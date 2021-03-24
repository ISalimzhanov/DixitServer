package server.lobby_service.jsonrpc.messages.requests

import com.fasterxml.jackson.annotation.JsonTypeName
import server.api.jsonrpc.JsonRpcRequest
import server.lobby_service.mongo.documents.lobbies.LobbyRegion

@JsonTypeName("start")
class StartGameRequest(
    params: Params,
    id: String,
) : JsonRpcRequest<StartGameRequest.Params>("start", params, id) {
    data class Params(
        val lobbyId: String,
    ) : JsonRpcRequest.Params()
}