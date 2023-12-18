package com.boriskaloshin.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.boriskaloshin.data.model.UserModel
import com.boriskaloshin.data.model.table.CustomerTable
import com.boriskaloshin.data.model.table.UserTable
import org.jetbrains.exposed.sql.Join
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.selectAll
import java.time.LocalDateTime
import java.time.ZoneOffset

class JwtService {
    private val issuer = "car-service-issuer"
    private val jwtSecret = System.getenv("JWT_SECRET")
    private val algorithm = Algorithm.HMAC512(jwtSecret)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    //генерация токена
    fun generateToken(user: UserModel): String {
        val join = Join(
            UserTable,
            CustomerTable,
            onColumn = UserTable.userCustomerId, otherColumn = CustomerTable.id,
            joinType = JoinType.LEFT
        )
        val email = join.selectAll().first()[CustomerTable.customerEmail]

        return JWT
            .create()
            .withSubject("car-service-subject")
            .withIssuer(issuer)
            .withClaim("email", email)
            .withExpiresAt(LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.UTC)) //время жизни 1 день
            .sign(algorithm)
    }
}