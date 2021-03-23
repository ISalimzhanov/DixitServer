package server.lobby_service.mongo.exceptions.player

class PlayerDoesntExistException : PlayerException("The user is not located in any lobby ") {
}