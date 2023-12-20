package com.boriskaloshin.domain.usecase

import com.boriskaloshin.data.model.CompanyModel
import com.boriskaloshin.domain.repository.CompanyRepository

class CompanyUseCase(
    val repository: CompanyRepository,
) {
    suspend fun createCompany(company: CompanyModel) = repository.insertCompany(company)

    suspend fun deleteCompany(name: String) = repository.deleteCompany(name)

    suspend fun findCompanyByName(name: String) = repository.findCompanyByName(name)
}