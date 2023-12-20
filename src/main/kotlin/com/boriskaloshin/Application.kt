package com.boriskaloshin

import com.boriskaloshin.auth.JwtService
import com.boriskaloshin.data.repository.CompanyRepositoryImpl
import com.boriskaloshin.data.repository.UserRepositoryImpl
import com.boriskaloshin.domain.usecase.CompanyUseCase
import com.boriskaloshin.domain.usecase.UserUseCase
import com.boriskaloshin.plugins.*
import com.boriskaloshin.plugins.DatabaseFactory.initializationDatabase
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    val jwtService = JwtService()
    val repositoryUser = UserRepositoryImpl()
    val repositoryCompany = CompanyRepositoryImpl()
    val userUseCase = UserUseCase(repositoryUser, jwtService)
    val companyUseCase = CompanyUseCase(repositoryCompany)

    initializationDatabase()
    configureMonitoring()
    configureSerialization()
    configureSecurity(userUseCase)
    configureRouting(userUseCase, companyUseCase)
    //configureHTTP()
}
