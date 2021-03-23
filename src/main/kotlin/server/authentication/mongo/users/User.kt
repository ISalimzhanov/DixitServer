package server.authentication.mongo.users

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "users")
data class User(
    @Indexed(unique = true)
    var alias: String,
    var password: String,
) {
    @Id
    lateinit var id: String
}