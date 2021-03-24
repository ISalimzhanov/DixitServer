package server.lobby_service.mongo.repositories

import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository
import server.lobby_service.mongo.documents.lobbies.Lobby
import server.lobby_service.mongo.documents.lobbies.LobbyRegion
import server.lobby.mongo.documents.players.Player
import server.lobby_service.mongo.documents.lobbies.LobbyState

@Suppress("SpringDataRepositoryMethodParametersInspection")
@Repository
interface LobbyRepository
    : MongoRepository<Lobby, String>, PagingAndSortingRepository<Lobby, String> {
    fun findLobbyById(id: String): Lobby?

    fun existsByName(name: String): Boolean

    fun findAllByNameContains(name: String): List<Lobby>

    fun findAllByNameContains(name: String, pageable: Pageable): List<Lobby>

    fun findAllByRegionAndNameContains(region: LobbyRegion, name: String): List<Lobby>

    fun findAllByRegionAndNameContains(region: LobbyRegion, name: String, pageable: Pageable): List<Lobby>

    fun findAllByCreatedAtBeforeAndState(time: Long?, state: LobbyState): List<Lobby>

    fun findAllByRegion(region: LobbyRegion): List<Lobby>

    fun findAllByRegion(region: LobbyRegion, pageable: Pageable): List<Lobby>

    fun findLobbyByPlayers(player: Player): Lobby?

    fun deleteLobbyById(id: String)
}
