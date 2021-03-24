package server.lobby.mongo.documents.players

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import server.authentication.mongo.documents.user.User

@Document(collection = "player")
data class Player(
    @Indexed(unique = true)
    @DBRef val user: User,
    val connector: String,
) {
    @Id
    lateinit var id: String
    fun setConnector(connector: String): Player {
        return Player(user, connector)
    }
}