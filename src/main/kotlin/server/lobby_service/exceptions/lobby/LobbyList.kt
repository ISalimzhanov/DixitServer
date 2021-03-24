package server.lobby_service.exceptions.lobby

import server.lobby_service.mongo.documents.lobbies.LobbySearchResult

data class LobbyList(
    val lobbies: List<LobbySearchResult>,
    val total: Int,
)