package server.lobby.mongo.player

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Player(
    @Id val userId: String,
    val connector: String,
) {
    fun setConnector(connector: String): Player {
        return Player(userId, connector)
    }
}