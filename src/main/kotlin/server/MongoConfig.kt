package server

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.MongoClientSettings
import com.mongodb.ConnectionString
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.core.MongoTemplate


@Configuration
@EnableMongoRepositories
class MongoConfig {
    val database = "server"

    fun mongoClient(): MongoClient {
        val connectionString = ConnectionString("mongodb://localhost:27017/${database}")
        val mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build()
        return MongoClients.create(mongoClientSettings)
    }

    @Bean(name = ["mongoTemplate"])
    fun getMongoTemplate(): MongoTemplate {
        return MongoTemplate(mongoClient(), database)
    }
}