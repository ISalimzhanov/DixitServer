package server.authentication.mongo.repositories

import org.springframework.data.mongodb.repository.MongoRepository
import server.authentication.mongo.documents.users.User

interface UserRepository : MongoRepository<User, String> {
    fun findByAlias(alias: String): User?
}