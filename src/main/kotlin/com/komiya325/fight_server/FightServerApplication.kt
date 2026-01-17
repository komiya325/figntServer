package com.komiya325.fight_server

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration

@SpringBootApplication
@PropertySource("classpath:application-secret.properties")
class FightServerApplication

fun main(args: Array<String>) {
    runApplication<FightServerApplication>(*args)
}

@Configuration
class MongoConfig : AbstractMongoClientConfiguration() {

    override fun getDatabaseName(): String = "fight"

    // 秘密ファイルから注入
    @Value("\${mongodb.atlas.uri}")
    private lateinit var atlasUri: String

    @Bean
    override fun mongoClient(): MongoClient {
        println("--- CONNECTING TO ATLAS USING SECRET FILE ---")
        return MongoClients.create(atlasUri)
    }
}