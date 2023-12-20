package com.boriskaloshin.data.model.request.company

import kotlinx.serialization.Serializable

@Serializable
data class CompanyRequest(
    val companyName: String,
    val companyAddress: String,
    val companyLongitude: Float,
    val companyLatitude: Float,
    val companyCreatedDate: String,
    )