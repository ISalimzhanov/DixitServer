package server.lobby_service.jsonrpc.messages.responses

import server.api.jsonrpc.JsonRpcError
import server.api.jsonrpc.JsonRpcResponse

class LeaveLobbyResponse(
    result: Unit?,
    error: JsonRpcError?,
    id: String,
) : JsonRpcResponse(result, error, id)