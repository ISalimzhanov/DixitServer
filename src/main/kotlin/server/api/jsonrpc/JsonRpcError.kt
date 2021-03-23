package server.api.jsonrpc

data class JsonRpcError(
    val code: Int,
    val message: String,
) {
}