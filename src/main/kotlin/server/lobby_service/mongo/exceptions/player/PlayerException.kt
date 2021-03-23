package server.lobby_service.mongo.exceptions.player

import server.lobby_service.mongo.exceptions.LobbyServiceException

open class PlayerException : LobbyServiceException {
    constructor() : super()
    constructor(message: String) : super(message)
}