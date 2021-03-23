package server.lobby.mongo.player

import org.springframework.data.mongodb.repository.MongoRepository

interface PlayerRepository : MongoRepository<Player, String> {
    fun findByUserId(userId: String): Player?
}