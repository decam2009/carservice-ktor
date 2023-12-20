package com.boriskaloshin

import com.boriskaloshin.auth.JwtService
import com.boriskaloshin.data.repository.UserRepositoryImpl
import com.boriskaloshin.domain.usecase.UserUseCase
import com.boriskaloshin.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest () {

    private val userUseCase by lazy { UserUseCase(
        repository = UserRepositoryImpl(),
        jwtService = JwtService(),
    ) }
    @Test
    fun testRoot() = testApplication {

        application {
            configureRouting(userUseCase)
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello World!", bodyAsText())
        }
    }
}
