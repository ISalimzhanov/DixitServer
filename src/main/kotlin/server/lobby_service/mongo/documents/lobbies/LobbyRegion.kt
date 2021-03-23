package server.lobby_service.mongo.documents.lobbies

import com.fasterxml.jackson.annotation.JsonValue

enum class LobbyRegion(
    @JsonValue val id: Int
) {
    ASIA(0),
    EUROPE(1),
    AFRICA(2),
    NORTH_AMERICA(3),
    SOUTH_AMERICA(4),
    AUSTRALIA(5),
}