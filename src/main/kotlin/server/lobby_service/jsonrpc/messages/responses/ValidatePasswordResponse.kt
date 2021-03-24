package server.lobby_service.jsonrpc.messages.responses

import server.api.jsonrpc.JsonRpcError
import server.api.jsonrpc.JsonRpcResponse

class ValidatePasswordResponse(
    result: Boolean?,
    error: JsonRpcError?,
    id: String,
) : JsonRpcResponse(result, error, id)