package com.boriskaloshin.plugins

import com.boriskaloshin.model.table.CompanyTable
import com.boriskaloshin.model.table.UserTable
import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Schema
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    private val dbUrl = System.getenv("DB_POSTGRES_URL")
    private val dbUser = System.getenv("DB_POSTGRES_USER")
    private val dbPassword = System.getenv("DB_POSTGRES_PASSWORD")
    private val dbSchema = System.getenv("DB_POSTGRES_SCHEMA")

    fun Application.initializationDatabase() {
        Database.connect(getHikariDatabase())

        transaction {
            SchemaUtils.createSchema(Schema(dbSchema))
            SchemaUtils.create(
                CompanyTable, UserTable
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

}