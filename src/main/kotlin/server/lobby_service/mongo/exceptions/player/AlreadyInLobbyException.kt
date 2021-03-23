package server.lobby_service.mongo.exceptions.player

class AlreadyInLobbyException : PlayerException("The user is already in the lobby") {
}