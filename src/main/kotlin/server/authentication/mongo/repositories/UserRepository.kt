package server.authentication.mongo.repositories

import org.springframework.data.mongodb.repository.MongoRepository
import server.authentication.mongo.documents.user.User

interface UserRepository : MongoRepository<User, String> {
    fun findByAlias(alias: String): User?
    fun findUserById(id: String): User?
}