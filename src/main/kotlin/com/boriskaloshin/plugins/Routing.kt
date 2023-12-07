package com.boriskaloshin.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }

    routing {
        get("/prelogin")
        {
            call.respondText { "prelogin" }
        }
    }
}
