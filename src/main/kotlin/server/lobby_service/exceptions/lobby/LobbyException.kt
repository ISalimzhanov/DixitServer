package server.lobby_service.exceptions.lobby

import server.lobby_service.exceptions.LobbyServiceException

open class LobbyException : LobbyServiceException {
    constructor() : super()
    constructor(message: String) : super(message)
}