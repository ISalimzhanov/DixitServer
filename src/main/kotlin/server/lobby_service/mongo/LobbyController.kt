package server.lobby_service.mongo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import server.api.jsonrpc.JsonRpcError
import server.api.jsonrpc.JsonRpcRequest
import server.api.jsonrpc.JsonRpcResponse
import server.authentication.mongo.exceptions.users.IncorrectPasswordException
import server.lobby_service.mongo.documents.lobbies.Lobby
import server.lobby_service.mongo.documents.lobbies.LobbyRegion
import server.lobby_service.mongo.repositories.LobbyRepository
import server.lobby_service.mongo.documents.lobbies.LobbyState
import server.lobby.mongo.documents.players.Player
import server.lobby_service.mongo.exceptions.lobby.LobbyDoesntExistException
import server.lobby_service.mongo.exceptions.player.PlayerException
import server.lobby_service.mongo.repositories.PlayerRepository
import java.util.*

@RestController
@CrossOrigin(origins = ["*"])
class LobbyController(
    @Autowired private val lobbyRepository: LobbyRepository,
    @Autowired private val playerRepository: PlayerRepository,
) {
    private fun createOrModifyPlayer(userId: String, connector: String): Player {
        var player: Player? = playerRepository.findByUserId(userId)
        if (player == null) {
            player = Player(userId, connector)
            playerRepository.save(player)
        } else {
            player.setConnector(connector)
        }
        return player
    }

    private fun createLobby(
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

    private fun joinLobby(
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

    private fun leaveLobby(
        userId: String,
        lobbyId: String,
    ): Unit {
        val player = playerRepository.findByUserId(userId) ?: throw PlayerException()
        var lobby = lobbyRepository.findLobbyById(lobbyId) ?: throw LobbyDoesntExistException()
        lobby = lobbyRepository.save(lobby.removePlayer(player))
        if (lobby.players.isEmpty()) {
            lobbyRepository.deleteLobbyById(lobbyId)
        }
        playerRepository.deleteByUserId(userId)
        return Unit
    }

    private fun findLobby(
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

    @RequestMapping("/api/lobbies", method = [RequestMethod.OPTIONS, RequestMethod.POST])
    fun handleRequest(@RequestBody request: JsonRpcRequest): ResponseEntity<JsonRpcResponse> {
        var result: Any? = null
        var error: JsonRpcError? = null
        when (request.method) {
            "create" -> {
                if (request.params["name"] == null || request.params["password"] == null
                    || request.params["region"] == null
                ) {
                    error = JsonRpcError(
                        code = -10,
                        message = "Some parameters are not defined"
                    )
                } else {
                    try {
                        result = createLobby(
                            name = request.params["name"]!! as String,
                            region = when (request.params["region"]!!) {
                                LobbyRegion.AFRICA.ordinal -> LobbyRegion.AFRICA
                                LobbyRegion.ASIA.ordinal -> LobbyRegion.ASIA
                                LobbyRegion.AUSTRALIA.ordinal -> LobbyRegion.AUSTRALIA
                                LobbyRegion.EUROPE.ordinal -> LobbyRegion.EUROPE
                                LobbyRegion.NORTH_AMERICA -> LobbyRegion.NORTH_AMERICA
                                LobbyRegion.SOUTH_AMERICA.ordinal -> LobbyRegion.SOUTH_AMERICA
                                else -> LobbyRegion.SOUTH_AMERICA
                            },
                            password = request.params["password"]!! as String
                        )
                    } catch (e: Exception) {
                        error = JsonRpcError(
                            code = -11,
                            message = "Opps" // toDo
                        )
                    }
                }
            }
            "join" -> {
                if (request.params["userId"] == null || request.params["connector"] == null
                    || request.params["password"] == null || request.params["lobbyId"] == null
                ) {
                    error = JsonRpcError(
                        code = -10,
                        message = "Some parameters are not defined"
                    )
                } else {
                    try {
                        result = joinLobby(
                            lobbyId = request.params["lobbyId"]!! as String,
                            userId = request.params["userId"]!! as String,
                            connector = request.params["connector"]!! as String,
                            password = request.params["password"]!! as String
                        )
                    } catch (e: Exception) {
                        error = JsonRpcError(
                            code = -11,
                            message = "Opps" // toDo
                        )
                    }
                }
            }

            "leave" -> {
                if (request.params["userId"] == null ||
                    request.params["lobbyId"] == null
                ) {
                    error = JsonRpcError(
                        code = -10,
                        message = "Some parameters are not defined"
                    )
                } else {
                    try {
                        result = leaveLobby(
                            userId = request.params["userId"]!! as String,
                            lobbyId = request.params["lobbyId"]!! as String
                        )
                    } catch (e: Exception) {
                        error = JsonRpcError(
                            code = -11,
                            message = "Opps" // toDo
                        )
                    }
                }
            }
            "find" -> {
                if (request.params["searchQuery"] == null ||
                    request.params["page"] == null || request.params["region"] == null
                    || request.params["pageSize"] == null
                ) {
                    error = JsonRpcError(
                        code = -10,
                        message = "Some parameters are not defined"
                    )
                } else {
                    try {
                        result = findLobby(
                            region = when (request.params["region"]!!) {
                                LobbyRegion.AFRICA.ordinal -> LobbyRegion.AFRICA
                                LobbyRegion.ASIA.ordinal -> LobbyRegion.ASIA
                                LobbyRegion.AUSTRALIA.ordinal -> LobbyRegion.AUSTRALIA
                                LobbyRegion.EUROPE.ordinal -> LobbyRegion.EUROPE
                                LobbyRegion.NORTH_AMERICA -> LobbyRegion.NORTH_AMERICA
                                LobbyRegion.SOUTH_AMERICA.ordinal -> LobbyRegion.SOUTH_AMERICA
                                else -> LobbyRegion.SOUTH_AMERICA
                            },
                            searchQuery = request.params["searchQuery"]!! as String,
                            page = request.params["page"]!! as Int,
                            pageSize = request.params["pageSize"]!! as Int,
                        )
                    } catch (e: Exception) {
                        error = JsonRpcError(
                            code = -13,
                            message = e.message!! // toDo
                        )
                    }
                }
            }
        }
        val response = JsonRpcResponse(result = result, error = error, id = request.id)
        return ResponseEntity.ok(response)
    }
}