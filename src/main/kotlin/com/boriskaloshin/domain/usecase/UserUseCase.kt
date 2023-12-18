package com.boriskaloshin.domain.usecase

import com.auth0.jwt.JWTVerifier
import com.boriskaloshin.auth.JwtService
import com.boriskaloshin.data.model.UserModel
import com.boriskaloshin.data.repository.UserRepositoryImpl

data class UserUseCase(
    val repository: UserRepositoryImpl,
    val jwtService: JwtService,
) {
    suspend fun createUser(user: UserModel) = repository.insertUser(user = user)
    suspend fun findUserByEmail(email: String) = repository.getUserByEmail(email = email)

    fun generateToken (user: UserModel):String = jwtService.generateToken(user)

    fun getJwtVerifier (): JWTVerifier = jwtService.verifier

}