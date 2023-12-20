package com.boriskaloshin.route

import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Route.ServiceRoute() {

    authenticate("jwt") {

        get("/api/v1/services") {

        }
    }

}