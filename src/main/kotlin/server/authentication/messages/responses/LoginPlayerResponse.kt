package server.authentication.messages.responses

import server.api.jsonrpc.JsonRpcError
import server.api.jsonrpc.JsonRpcResponse

class LoginPlayerResponse(
    userId: String?,
    error: JsonRpcError?,
    id: String
) : JsonRpcResponse(userId, error, id)