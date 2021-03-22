package authentication.mongo.users

import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<User, String> {
    fun findByAlias(alias: String): User?
}