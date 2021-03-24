package server.lobby_service.jsonrpc.messages.responses

import server.api.jsonrpc.JsonRpcError
import server.api.jsonrpc.JsonRpcResponse
import server.lobby.mongo.documents.players.Player
import server.lobby_service.mongo.documents.players.PlayerInfo

class JoinLobbyResponse(
    players: List<PlayerInfo>?,
    error: JsonRpcError?,
    id: String,
) : JsonRpcResponse(players, error, id)