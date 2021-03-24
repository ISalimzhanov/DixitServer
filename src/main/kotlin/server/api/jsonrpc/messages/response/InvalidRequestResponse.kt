package server.api.jsonrpc.messages.response

import server.api.jsonrpc.JsonRpcError
import server.api.jsonrpc.JsonRpcResponse

class InvalidRequestResponse() : JsonRpcResponse(null, JsonRpcError(3, "Invalid request"), "") {
}