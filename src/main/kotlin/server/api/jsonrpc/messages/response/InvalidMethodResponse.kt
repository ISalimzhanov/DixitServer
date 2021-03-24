package server.api.jsonrpc.messages.response

import server.api.jsonrpc.JsonRpcError
import server.api.jsonrpc.JsonRpcResponse

class InvalidMethodResponse(id: String) : JsonRpcResponse(null, JsonRpcError(1, "Invalid method"), id) {
}