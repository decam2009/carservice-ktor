package com.boriskaloshin.data.model

import io.ktor.server.auth.*
import kotlinx.serialization.Serializable

@Serializable
data class CompanyModel (
    val id: Int,
    val companyName: String,
    val companyAddress: String,
    val companyLongitude: Float,
    val companyLatitude: Float,
    val companyCreatedDate: String
) : Principal