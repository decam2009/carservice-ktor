package com.boriskaloshin.plugins

import com.boriskaloshin.data.model.table.CarTable
import com.boriskaloshin.data.model.table.CompanyTable
import com.boriskaloshin.data.model.table.UserTable
import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Schema
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.nio.channels.ShutdownChannelGroupException

object DatabaseFactory {
    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    private val dbUrl = System.getenv("DB_POSTGRES_URL")
    private val dbUser = System.getenv("DB_POSTGRES_USER")
    private val dbPassword = System.getenv("DB_POSTGRES_PASSWORD")
    private val dbSchema = System.getenv("DB_POSTGRES_SCHEMA")

    //Генерация последовательности
    val myseq = org.jetbrains.exposed.sql.Sequence(
        name = "my_sequence",
        startWith = 1,
        incrementBy = 1,
        minValue = 1,
        maxValue = 10000,
        cycle = false
    )

    fun Application.initializationDatabase() {
        Database.connect(getHikariDatabase())

        transaction {
            SchemaUtils.createSchema(Schema(dbSchema))
            SchemaUtils.setSchema(Schema(dbSchema))
            //SchemaUtils.createSequence(myseq) //Создание последовательности
            SchemaUtils.create(
                CompanyTable, UserTable, CarTable
            )
            commit()
        }

    }

    private fun getHikariDatabase(): HikariDataSource {
        println("DB_URL: $dbUrl")
        println("DB_USER: $dbUser")

        val config = HikariConfig()
        with(config) {
            driverClassName = "org.postgresql.Driver"
            jdbcUrl = dbUrl
            username = dbUser
            password = dbPassword
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            schema = dbSchema
            validate()
        }
        return HikariDataSource(config)
    }



    suspend fun <T> dbQuery(block: () -> T): T {
        return withContext(Dispatchers.IO) {
            transaction { block() }
        }
    }
}