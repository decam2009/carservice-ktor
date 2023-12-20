package com.boriskaloshin.domain.repository

import com.boriskaloshin.data.model.CompanyModel

interface CompanyRepository {
    suspend fun insertCompany (company: CompanyModel)
    suspend fun deleteCompany (name: String)
    suspend fun findCompanyByName (name: String): CompanyModel?

}