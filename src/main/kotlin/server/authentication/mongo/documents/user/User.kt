package server.authentication.mongo.documents.user

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user")
data class User(
    @Indexed(name = "alias", unique = true)
    var alias: String,
    var password: String,
) {
    @Id
    lateinit var id: String
}