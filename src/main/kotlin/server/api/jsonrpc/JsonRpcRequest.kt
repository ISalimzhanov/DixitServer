package server.api.jsonrpc

import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "method"
)
@RequestTypes
open class JsonRpcRequest<ParamsType : JsonRpcRequest.Params>(
    val method: String,
    val params: ParamsType,
    val id: String,
) {
    abstract class Params
}