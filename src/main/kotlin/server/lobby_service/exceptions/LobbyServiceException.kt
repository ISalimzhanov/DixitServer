package server.lobby_service.exceptions

open class LobbyServiceException : Exception {
    constructor() : super()
    constructor(message: String) : super(message)
}