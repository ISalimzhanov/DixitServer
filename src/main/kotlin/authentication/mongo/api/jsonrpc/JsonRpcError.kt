package authentication.mongo.api.jsonrpc

data class JsonRpcError(
    val code: Int,
    val message: String,
) {
}