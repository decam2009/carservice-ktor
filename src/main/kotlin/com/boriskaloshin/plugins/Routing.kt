package com.boriskaloshin.plugins

import com.boriskaloshin.domain.usecase.CompanyUseCase
import com.boriskaloshin.domain.usecase.UserUseCase
import com.boriskaloshin.route.CompanyRoute
import com.boriskaloshin.route.UserRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(userUseCase: UserUseCase, companyUseCase: CompanyUseCase) {
    routing {
        UserRoute (userUseCase = userUseCase)
        CompanyRoute (companyUseCase = companyUseCase)

    }
}
