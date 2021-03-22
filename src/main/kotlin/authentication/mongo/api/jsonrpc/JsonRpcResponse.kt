package authentication.mongo.api.jsonrpc

data class JsonRpcResponse(
    val result: Any?,
    val error: JsonRpcError? = null,
    val id: String,
) {
}