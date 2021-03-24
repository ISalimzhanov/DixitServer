package server.lobby_service.mongo.repositories

import org.springframework.data.mongodb.repository.MongoRepository
import server.authentication.mongo.documents.user.User
import server.lobby.mongo.documents.players.Player

interface PlayerRepository : MongoRepository<Player, String> {
    fun findByUserId(userId: String): Player?
    fun deleteByUserId(userId: String)
    fun deleteAllByUserId(userId: String)
    fun findByUser(user: User): Player?
}