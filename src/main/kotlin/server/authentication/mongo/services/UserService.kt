package server.authentication.mongo.services

import org.springframework.stereotype.Service
import server.authentication.mongo.documents.user.User
import server.authentication.exceptions.user.IncorrectPasswordException
import server.authentication.exceptions.user.UserDoesntExistException
import server.authentication.mongo.repositories.UserRepository

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun login(alias: String, password: String): String {
        val user: User = userRepository.findByAlias(alias)
            ?: throw UserDoesntExistException()
        if (user.password != password)
            throw IncorrectPasswordException()
        return user.id
    }

    fun register(alias: String, password: String): String {
        val user = User(alias, password)
        userRepository.save(user)
        return user.id
    }
}