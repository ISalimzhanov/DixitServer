package authentication.mongo.api.jsonrpc

data class JsonRpcRequest(
    val method: String,
    val params: Map<String, Any?>,
    val id: String,
) {
}