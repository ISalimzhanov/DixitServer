package server.api.jsonrpc.messages.response

import server.api.jsonrpc.JsonRpcError
import server.api.jsonrpc.JsonRpcResponse

class InvalidJsonResponse() : JsonRpcResponse(null, JsonRpcError(0, "Invalid JSON"), "") {
}