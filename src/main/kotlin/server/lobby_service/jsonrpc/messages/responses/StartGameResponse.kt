package server.lobby_service.jsonrpc.messages.responses

import server.api.jsonrpc.JsonRpcError
import server.api.jsonrpc.JsonRpcResponse

class StartGameResponse(
    seed: Int?,
    error: JsonRpcError?,
    id: String,
) : JsonRpcResponse(seed, error, id)