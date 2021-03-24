package server.lobby_service.exceptions

import server.lobby_service.exceptions.lobby.LobbyException

class LobbyExistsException : LobbyException("Lobby with such name already exists")