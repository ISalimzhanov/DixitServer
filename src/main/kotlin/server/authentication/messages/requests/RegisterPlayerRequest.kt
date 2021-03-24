package server.authentication.messages.requests

import com.fasterxml.jackson.annotation.JsonTypeName
import server.api.jsonrpc.JsonRpcRequest

@JsonTypeName("register")
class RegisterPlayerRequest(
    params: Params,
    id: String,
) : JsonRpcRequest<RegisterPlayerRequest.Params>("register", params, id) {
    data class Params(
        val alias: String,
        val password: String,
    ) : JsonRpcRequest.Params()
}