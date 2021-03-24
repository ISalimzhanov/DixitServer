package server.authentication.messages.requests

import com.fasterxml.jackson.annotation.JsonTypeName
import server.api.jsonrpc.JsonRpcRequest

@JsonTypeName("login")
class LoginPlayerRequest(
    params: Params,
    id: String,
) : JsonRpcRequest<LoginPlayerRequest.Params>("login", params, id) {
    data class Params(
        val alias: String,
        val password: String,
    ) : JsonRpcRequest.Params()
}