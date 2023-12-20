package com.boriskaloshin.data.model.responce

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponce(
    val success: Boolean,
    val message: String,
)