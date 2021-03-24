package server.lobby_service.jsonrpc.messages.responses

import server.api.jsonrpc.JsonRpcError
import server.api.jsonrpc.JsonRpcResponse
import server.lobby_service.exceptions.lobby.LobbyList

class FindLobbyResponse(
    lobbyList: LobbyList?,
    error: JsonRpcError?,
    id: String,
) : JsonRpcResponse(lobbyList, error, id)