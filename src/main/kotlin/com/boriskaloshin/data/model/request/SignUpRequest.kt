package com.boriskaloshin.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val email: String,
    val login: String,
    val password: String,
    val firstName: String,
    val lastname: String,
    val phoneNumber: String,
    val role: String
)