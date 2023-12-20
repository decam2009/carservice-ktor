package com.boriskaloshin.data.model

import io.ktor.server.auth.*
import kotlinx.serialization.Serializable

@Serializable
class UserModel(
    val id: Int,
    val email: String,
    val login: String,
    val password: String,
    val firstName: String,
    val lastname:String,
    val phoneNumber: String,
    val role: RoleModel,
) : Principal {
}