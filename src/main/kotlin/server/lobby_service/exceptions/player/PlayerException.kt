package server.lobby_service.exceptions.player

import server.lobby_service.exceptions.LobbyServiceException

open class PlayerException : LobbyServiceException {
    constructor() : super()
    constructor(message: String) : super(message)
}