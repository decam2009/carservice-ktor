package com.boriskaloshin.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SigninRequest (
    val email: String,
    val password: String
)