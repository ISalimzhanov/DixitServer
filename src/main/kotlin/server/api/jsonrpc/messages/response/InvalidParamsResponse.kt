package server.api.jsonrpc.messages.response

import server.api.jsonrpc.JsonRpcError
import server.api.jsonrpc.JsonRpcResponse

class InvalidParamsResponse() : JsonRpcResponse(null, JsonRpcError(2, "Invalid params"), "") {
}