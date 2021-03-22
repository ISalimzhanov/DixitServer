package authentication.mongo

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.core.MongoClientFactoryBean

@Configuration
@EnableMongoRepositories
class MongoConfig {
}