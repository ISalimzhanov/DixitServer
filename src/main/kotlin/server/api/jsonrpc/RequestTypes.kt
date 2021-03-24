package server.api.jsonrpc

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside
import com.fasterxml.jackson.annotation.JsonSubTypes
import server.authentication.messages.requests.LoginPlayerRequest
import server.authentication.messages.requests.RegisterPlayerRequest
import server.lobby_service.jsonrpc.messages.requests.*

@JacksonAnnotationsInside
@JsonSubTypes(
    JsonSubTypes.Type(FindLobbyRequest::class),
    JsonSubTypes.Type(CreateLobbyRequest::class),
    JsonSubTypes.Type(JoinLobbyRequest::class),
    JsonSubTypes.Type(LeaveLobbyRequest::class),
    JsonSubTypes.Type(ValidatePasswordRequest::class),
    JsonSubTypes.Type(RegisterPlayerRequest::class),
    JsonSubTypes.Type(LoginPlayerRequest::class),
    JsonSubTypes.Type(StartGameRequest::class),
)
annotation class RequestTypes