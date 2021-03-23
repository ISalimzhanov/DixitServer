package server.lobby_service.mongo

data class LobbyList(
    val lobbies: List<LobbySearchResult>,
    val total: Int,
) {

}