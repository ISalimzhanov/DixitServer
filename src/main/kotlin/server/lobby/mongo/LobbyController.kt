package server.lobby.mongo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import server.api.jsonrpc.JsonRpcError
import server.api.jsonrpc.JsonRpcRequest
import server.api.jsonrpc.JsonRpcResponse
import server.lobby.mongo.lobbies.Lobby
import server.lobby.mongo.lobbies.LobbyRegion
import server.lobby.mongo.lobbies.LobbyRepository
import server.lobby.mongo.lobbies.LobbyState
import server.lobby.mongo.player.Player
import server.lobby.mongo.player.PlayerRepository
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
        userId: String,
        connector: String,
        name: String,
        passwordKey: String?,
        region: LobbyRegion,
    ): String {
        val player = createOrModifyPlayer(userId, connector)
        val lobby = Lobby(
            name = name,
            passwordKey = passwordKey,
            region = region,
            state = LobbyState.CREATED,
            seed = Random().nextInt(),
            players = listOf(player)
        )
        lobbyRepository.save(lobby)
        return lobby.id
    }

    private fun deleteLobby(userId: String, lobbyId: String) {
        val lobby = lobbyRepository.findLobbyById(lobbyId) ?: throw Exception() //toDo
        if (lobby.players[0].userId != userId) {
            throw Exception() //toDo
        }
        lobbyRepository.deleteLobbyById(lobbyId)
    }


    private fun joinLobby(
        userId: String,
        connector: String,
        lobbyId: String,
        passwordKey: String?
    ): List<Player> {
        val player = createOrModifyPlayer(userId, connector)
        val lobby = lobbyRepository.findLobbyById(lobbyId) ?: throw Exception() //toDo
        if (lobby.passwordKey != passwordKey)
            throw Exception() //toDo
        lobby.addPlayer(player)
        return lobby.players
    }

    private fun leaveLobby(
        userId: String,
        lobbyId: String,
    ) {
        val player = playerRepository.findByUserId(userId) ?: throw Exception() //toDo
        val lobby = lobbyRepository.findLobbyById(lobbyId) ?: throw Exception() //toDo
        lobby.removePlayer(player)
        if (lobby.players.isEmpty()) {
            lobbyRepository.deleteLobbyById(lobbyId)
        }
    }

    private fun findLobby(
        region: LobbyRegion?,
        searchQuery: String?,
        page: Int,
        pageSize: Int,
    ) {
        val pager = PageRequest.of(page, pageSize)
        val lobbies: List<Lobby> = lobbyRepository.findAllByRegionAndNameContains(
            region, searchQuery, pager
        )
    }

    @RequestMapping("/api/lobbies", method = [RequestMethod.OPTIONS, RequestMethod.POST])
    fun handleRequest(@RequestBody request: JsonRpcRequest): ResponseEntity<JsonRpcResponse> {
        var result: Any? = null
        var error: JsonRpcError? = null
        when (request.method) {
            "createLobby" -> {
                if (request.params["user_id"] == null || request.params["connector"] == null
                    || request.params["name"] == null || request.params["passwordKey"] == null
                    || request.params["region"] == null
                ) {
                    error = JsonRpcError(
                        code = -10,
                        message = "Some parameters are not defined"
                    )
                } else {
                    try {
                        result = createLobby(
                            userId = request.params["user_id"]!! as String,
                            connector = request.params["connector"]!! as String,
                            name = request.params["name"]!! as String,
                            region = request.params["region"]!! as LobbyRegion,
                            passwordKey = request.params["passwordKey"]!! as String
                        )
                    } catch (e: Exception) {
                        error = JsonRpcError(
                            code = -11,
                            message = "Opps" // toDo
                        )
                    }
                }
            }
            "joinLobby" -> {
                if (request.params["user_id"] == null || request.params["connector"] == null
                    || request.params["passwordKey"] == null || request.params["lobby_id"] == null
                ) {
                    error = JsonRpcError(
                        code = -10,
                        message = "Some parameters are not defined"
                    )
                } else {
                    try {
                        result = joinLobby(
                            lobbyId = request.params["lobby_id"]!! as String,
                            userId = request.params["user_id"]!! as String,
                            connector = request.params["connector"]!! as String,
                            passwordKey = request.params["passwordKey"]!! as String
                        )
                    } catch (e: Exception) {
                        error = JsonRpcError(
                            code = -11,
                            message = "Opps" // toDo
                        )
                    }
                }
            }

            "leaveLobby" -> {
                if (request.params["user_id"] == null ||
                    request.params["lobby_id"] == null
                ) {
                    error = JsonRpcError(
                        code = -10,
                        message = "Some parameters are not defined"
                    )
                } else {
                    try {
                        result = leaveLobby(
                            userId = request.params["user_id"]!! as String,
                            lobbyId = request.params["lobby_id"]!! as String
                        )
                    } catch (e: Exception) {
                        error = JsonRpcError(
                            code = -11,
                            message = "Opps" // toDo
                        )
                    }
                }
            }
            "findLobby" -> {
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
                            region = request.params["region"]!! as LobbyRegion,
                            searchQuery = request.params["searchQuery"]!! as String,
                            page = request.params["page"]!! as Int,
                            pageSize = request.params["pageSize"]!! as Int,
                        )
                    } catch (e: Exception) {
                        error = JsonRpcError(
                            code = -13,
                            message = "Opps" // toDo
                        )
                    }
                }
            }
        }
        val response = JsonRpcResponse(result = result, error = error, id = request.id)
        return ResponseEntity.ok(response)
    }
}