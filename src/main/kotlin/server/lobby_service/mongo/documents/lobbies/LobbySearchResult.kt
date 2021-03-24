package server.lobby_service.mongo.documents.lobbies

data class LobbySearchResult(
    val id: String,
    val name: String,
    val region: LobbyRegion,
    val players: Int,
)