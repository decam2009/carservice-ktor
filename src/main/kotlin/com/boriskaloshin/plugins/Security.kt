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

    runBlocking {
        userUseCase.createUser(
            UserModel(
                id = 1,
                email = "test@test.com",
                login = "test@test.com",
                password = "123",
                firstName = "DemoName",
                lastname = "DemoLastName",
                phoneNumber = "+79222000000",
                isActive = false,
                role = RoleModel.CLIENT
            )
        )
    }

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
