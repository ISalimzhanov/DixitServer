package server.lobby_service.mongo.services

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import server.authentication.exceptions.user.IncorrectPasswordException
import server.authentication.exceptions.user.UserDoesntExistException
import server.authentication.mongo.documents.user.User
import server.authentication.mongo.repositories.UserRepository
import server.lobby.mongo.documents.players.Player
import server.lobby_service.exceptions.lobby.LobbyList
import server.lobby_service.mongo.documents.lobbies.Lobby
import server.lobby_service.mongo.documents.lobbies.LobbyRegion
import server.lobby_service.mongo.documents.lobbies.LobbySearchResult
import server.lobby_service.mongo.documents.lobbies.LobbyState
import server.lobby_service.exceptions.lobby.LobbyDoesntExistException
import server.lobby_service.exceptions.player.PlayerDoesntExistException
import server.lobby_service.exceptions.player.PlayerException
import server.lobby_service.mongo.repositories.LobbyRepository
import server.lobby_service.mongo.repositories.PlayerRepository
import java.util.*

@Service
class LobbyService(
    private val lobbyRepository: LobbyRepository,
    private val playerRepository: PlayerRepository,
    private val userRepository: UserRepository,
) {
    private fun createOrModifyPlayer(userId: String, connector: String): Player {
        var player: Player? = playerRepository.findByUserId(userId)
        if (player == null) {
            player = Player(userId, connector)
            playerRepository.save(player)
        } else {
            player.setConnector(connector)
        }
        val user: User = userRepository.findUserById(userId) ?: throw UserDoesntExistException()
        return player
    }

    fun createLobby(
        name: String,
        password: String?,
        region: LobbyRegion,
    ): String {
        val lobby = Lobby(
            name = name,
            password = password,
            region = region,
            state = LobbyState.CREATED,
            seed = Random().nextInt(),
            players = listOf()
        )
        lobbyRepository.save(lobby)
        return lobby.id
    }

    fun joinLobby(
        userId: String,
        connector: String,
        lobbyId: String,
        password: String?
    ): List<Player> {
        val player = createOrModifyPlayer(userId, connector)
        val lobby = lobbyRepository.findLobbyById(lobbyId) ?: throw LobbyDoesntExistException()
        if (lobby.password != password)
            throw IncorrectPasswordException()
        lobbyRepository.save(lobby.addPlayer(player))
        return lobby.players
    }

    fun leaveLobby(
        userId: String,
        lobbyId: String,
    ): Unit {
        val player = playerRepository.findByUserId(userId) ?: throw PlayerDoesntExistException()
        var lobby = lobbyRepository.findLobbyById(lobbyId) ?: throw LobbyDoesntExistException()
        lobby = lobbyRepository.save(lobby.removePlayer(player))
        if (lobby.players.isEmpty()) {
            lobbyRepository.deleteLobbyById(lobbyId)
        }
        playerRepository.deleteByUserId(userId)
    }

    fun findLobby(
        region: LobbyRegion,
        searchQuery: String,
        page: Int,
        pageSize: Int,
    ): LobbyList {
        val pager = PageRequest.of(page, pageSize)
        val lobbies: List<Lobby> = lobbyRepository.findAllByRegionAndNameContains(
            region, searchQuery, pager
        )
        val searchResults = lobbies.map {
            LobbySearchResult(
                id = it.id,
                name = it.name,
                region = it.region,
                players = it.players.size
            )
        }
        return LobbyList(searchResults, searchResults.size)
    }

    fun validatePassword(
        lobbyId: String,
        password: String?,
    ): Boolean {
        val lobby = lobbyRepository.findLobbyById(lobbyId) ?: throw LobbyDoesntExistException()
        if (lobby.password == password)
            return true
        return false
    }
}