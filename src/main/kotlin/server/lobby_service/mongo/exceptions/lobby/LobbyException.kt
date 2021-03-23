package server.lobby_service.mongo.exceptions.lobby

import server.lobby_service.mongo.exceptions.LobbyServiceException

open class LobbyException : LobbyServiceException {
    constructor() : super()
    constructor(message: String) : super(message)
}