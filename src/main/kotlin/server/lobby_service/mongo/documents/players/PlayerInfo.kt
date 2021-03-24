package server.lobby_service.mongo.documents.players

data class PlayerInfo(
    val userId: String,
    val connector: String,
    val alias: String,
)