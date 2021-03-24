package server.lobby_service.jsonrpc.messages.responses

import server.api.jsonrpc.JsonRpcError
import server.api.jsonrpc.JsonRpcResponse

class CreateLobbyResponse(
    lobbyId: String?,
    error: JsonRpcError?,
    id: String,
) : JsonRpcResponse(lobbyId, error, id)