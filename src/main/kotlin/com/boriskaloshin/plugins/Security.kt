package com.boriskaloshin.plugins

import com.boriskaloshin.data.repository.UserRepositoryImpl
import com.boriskaloshin.auth.JwtService
import com.boriskaloshin.data.model.RoleModel
import com.boriskaloshin.data.model.UserModel
import com.boriskaloshin.domain.usecase.UserUseCase
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import kotlinx.coroutines.runBlocking

fun Application.configureSecurity(userUseCase: UserUseCase) {

    authentication {
        jwt("jwt") {
            verifier(userUseCase.getJwtVerifier())
            realm = "car-service-realm"
            validate {
                val payload = it.payload
                val email = payload.getClaim("email").asString()
                val user = userUseCase.findUserByEmail(email)
                user
            }
        }
    }

}
