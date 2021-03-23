package server.lobby.mongo.lobbies

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import server.lobby.mongo.player.Player

@Document
data class Lobby(
    val name: String,
    val passwordKey: String?,
    val region: LobbyRegion,
    var state: LobbyState,
    val seed: Int,
    @DBRef var players: List<Player>
) {
    @Id
    lateinit var id: String
    fun addPlayer(player: Player): Lobby {
        if (players.contains(player))
            throw Exception() //toDo
        val lobby = Lobby(
            name, passwordKey, region, state, seed,
            players = (players + listOf(player))
        )
        lobby.id = id
        return lobby
    }

    fun removePlayer(player: Player): Lobby {
        if (!players.contains(player))
            throw Exception() //toDo
        val lobby = Lobby(
            name, passwordKey, region, state, seed,
            players = (players - listOf(player))
        )
        lobby.id = id
        return lobby
    }

    fun setState(state: LobbyState): Lobby {
        val lobby = Lobby(
            name, passwordKey, region, state, seed,
            players
        )
        lobby.id = id
        return lobby
    }
}