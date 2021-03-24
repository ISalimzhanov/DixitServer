package server.lobby_service.mongo.services

import org.springframework.dao.DuplicateKeyException
import org.springframework.stereotype.Service
import server.api.jsonrpc.JsonRpcError
import server.authentication.exceptions.user.UserDoesntExistException
import server.authentication.exceptions.user.UserException
import server.lobby_service.exceptions.lobby.LobbyException
import server.lobby_service.exceptions.player.PlayerException
import server.lobby_service.jsonrpc.messages.requests.*
import server.lobby_service.jsonrpc.messages.responses.*

@Service
class LobbyHandler(
    private val lobbyService: LobbyService,
) {
    fun create(request: CreateLobbyRequest): CreateLobbyResponse {
        return try {
            CreateLobbyResponse(
                lobbyService.createLobby(request.params.name, request.params.password, request.params.region),
                null,
                request.id
            )
        } catch (e: DuplicateKeyException) {
            CreateLobbyResponse(
                null,
                JsonRpcError(code = -20, message = e.message!!),
                request.id
            )
        } catch (e: LobbyException) {
            CreateLobbyResponse(
                null,
                JsonRpcError(code = -20, message = e.message!!),
                request.id
            )
        }
    }

    fun join(request: JoinLobbyRequest): JoinLobbyResponse {
        return try {
            JoinLobbyResponse(
                lobbyService.joinLobby(
                    request.params.userId,
                    request.params.connector,
                    request.params.lobbyId,
                    request.params.password
                ),
                null,
                request.id
            )
        } catch (e: LobbyException) {
            JoinLobbyResponse(
                null,
                JsonRpcError(code = -21, message = e.message!!),
                request.id
            )
        } catch (e: PlayerException) {
            JoinLobbyResponse(
                null,
                JsonRpcError(code = -21, message = e.message!!),
                request.id
            )
        } catch (e: UserException) {
            JoinLobbyResponse(
                null,
                JsonRpcError(code = -22, message = e.message!!),
                request.id
            )
        }
    }

    fun leave(request: LeaveLobbyRequest): LeaveLobbyResponse {
        return try {
            LeaveLobbyResponse(
                lobbyService.leaveLobby(request.params.userId, request.params.lobbyId),
                null,
                request.id
            )
        } catch (e: LobbyException) {
            LeaveLobbyResponse(
                null,
                JsonRpcError(code = -22, message = e.message!!),
                request.id
            )
        } catch (e: PlayerException) {
            LeaveLobbyResponse(
                null,
                JsonRpcError(code = -22, message = e.message!!),
                request.id
            )
        } catch (e: UserException) {
            LeaveLobbyResponse(
                null,
                JsonRpcError(code = -22, message = e.message!!),
                request.id
            )
        }
    }

    fun find(request: FindLobbyRequest): FindLobbyResponse {
        return try {
            FindLobbyResponse(
                lobbyService.findLobby(
                    request.params.region,
                    request.params.searchQuery,
                    request.params.page,
                    request.params.pageSize
                ),
                null,
                request.id
            )
        } catch (e: LobbyException) {
            FindLobbyResponse(
                null,
                JsonRpcError(code = -22, message = e.message!!),
                request.id
            )
        }
    }

    fun validatePassword(request: ValidatePasswordRequest): ValidatePasswordResponse {
        return try {
            ValidatePasswordResponse(
                lobbyService.validatePassword(request.params.lobbyId, request.params.password),
                null,
                request.id
            )
        } catch (e: LobbyException) {
            ValidatePasswordResponse(
                null,
                JsonRpcError(code = -22, message = e.message!!),
                request.id
            )
        }
    }
}