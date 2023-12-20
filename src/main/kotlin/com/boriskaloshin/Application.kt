package com.boriskaloshin

import com.boriskaloshin.auth.JwtService
import com.boriskaloshin.data.repository.UserRepositoryImpl
import com.boriskaloshin.domain.usecase.UserUseCase
import com.boriskaloshin.plugins.*
import com.boriskaloshin.plugins.DatabaseFactory.initializationDatabase
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    val jwtService = JwtService()
    val repository = UserRepositoryImpl()
    val userUseCase = UserUseCase(repository, jwtService)

    initializationDatabase()
    configureMonitoring()
    configureSerialization()
    configureSecurity(userUseCase)
    configureRouting(userUseCase)
    //configureHTTP()
}
