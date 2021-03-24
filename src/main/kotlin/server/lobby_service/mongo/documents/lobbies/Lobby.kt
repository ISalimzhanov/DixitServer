package server.lobby_service.mongo.documents.lobbies

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import server.lobby.mongo.documents.players.Player
import server.lobby_service.exceptions.player.AlreadyInLobbyException
import server.lobby_service.exceptions.player.NotInLobbyException

@Document(collection = "lobby")
data class Lobby(
    @Indexed(unique = true)
    val name: String,
    val password: String?,
    val region: LobbyRegion,
    var state: LobbyState,
    val seed: Int,
    @DBRef var players: List<Player>
) {
    @Id
    lateinit var id: String
    fun addPlayer(player: Player): Lobby {
        if (players.contains(player))
            throw AlreadyInLobbyException()
        val lobby = Lobby(
            name, password, region, state, seed,
            players = (players + listOf(player))
        )
        lobby.id = id
        return lobby
    }

    fun removePlayer(player: Player): Lobby {
        if (!players.contains(player))
            throw NotInLobbyException()
        val lobby = Lobby(
            name, password, region, state, seed,
            players = (players - listOf(player))
        )
        lobby.id = id
        return lobby
    }

    fun setState(state: LobbyState): Lobby {
        val lobby = Lobby(
            name, password, region, state, seed,
            players
        )
        lobby.id = id
        return lobby
    }
}