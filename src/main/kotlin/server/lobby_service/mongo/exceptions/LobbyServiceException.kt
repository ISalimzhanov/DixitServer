package server.lobby_service.mongo.exceptions

open class LobbyServiceException : Exception {
    constructor() : super()
    constructor(message: String) : super(message)
}