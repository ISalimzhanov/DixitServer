package server.lobby_service.jsonrpc.messages.requests

import com.fasterxml.jackson.annotation.JsonTypeName
import server.api.jsonrpc.JsonRpcRequest
import server.lobby_service.mongo.documents.lobbies.LobbyRegion

@JsonTypeName("create")
class CreateLobbyRequest(
    params: Params,
    id: String,
) : JsonRpcRequest<CreateLobbyRequest.Params>("create", params, id) {
    data class Params(
        val name: String,
        val password: String,
        val region: LobbyRegion,
    ) : JsonRpcRequest.Params()
}