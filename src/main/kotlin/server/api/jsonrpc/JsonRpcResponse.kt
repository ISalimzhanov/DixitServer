package server.api.jsonrpc

open class JsonRpcResponse(
    val result: Any?,
    val error: JsonRpcError? = null,
    val id: String,
) {
}