package com.boriskaloshin

import com.boriskaloshin.plugins.*
import com.boriskaloshin.plugins.DatabaseFactory.initializationDatabase
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    initializationDatabase()
    configureMonitoring()
    configureSerialization()
    //configureSecurity()
    //configureHTTP()
    //configureRouting()
}
