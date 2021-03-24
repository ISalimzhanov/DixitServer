package server.lobby_service.jsonrpc.messages.responses

import server.api.jsonrpc.JsonRpcError
import server.api.jsonrpc.JsonRpcResponse
import server.lobby.mongo.documents.players.Player

class JoinLobbyResponse(
    players: List<Player>?,
    error: JsonRpcError?,
    id: String,
) : JsonRpcResponse(players, error, id)