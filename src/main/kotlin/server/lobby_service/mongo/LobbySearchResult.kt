package server.lobby_service.mongo

import server.lobby_service.mongo.documents.lobbies.LobbyRegion

data class LobbySearchResult(
    val id: String,
    val name: String,
    val region: LobbyRegion,
    val players: Int,
)