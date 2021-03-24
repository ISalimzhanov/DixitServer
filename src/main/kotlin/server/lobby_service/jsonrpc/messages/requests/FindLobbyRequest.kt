package server.lobby_service.jsonrpc.messages.requests

import com.fasterxml.jackson.annotation.JsonTypeName
import server.api.jsonrpc.JsonRpcRequest
import server.lobby_service.mongo.documents.lobbies.LobbyRegion

@JsonTypeName("find")
class FindLobbyRequest(
    params: Params,
    id: String,
) : JsonRpcRequest<FindLobbyRequest.Params>(
    "find",
    params,
    id
) {
    data class Params(
        val searchQuery: String,
        val region: LobbyRegion,
        val page: Int,
        val pageSize: Int,
    ) : JsonRpcRequest.Params()
}