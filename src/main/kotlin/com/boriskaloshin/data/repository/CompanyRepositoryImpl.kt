package com.boriskaloshin.data.repository

import com.boriskaloshin.data.model.CompanyModel
import com.boriskaloshin.data.model.table.CompanyTable
import com.boriskaloshin.domain.repository.CompanyRepository
import com.boriskaloshin.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class CompanyRepositoryImpl : CompanyRepository {
    override suspend fun insertCompany(company: CompanyModel) {
        return dbQuery {
            CompanyTable.insert { table ->
                table[companyName] = company.companyName
                table[companyAddress] = company.companyAddress
                table[companyLongitude] = company.companyLongitude
                table[companyLatitude] = company.companyLatitude
                table[companyCreatedDate] = company.companyCreatedDate
            }
        }
    }

    override suspend fun deleteCompany(name: String) {
        return dbQuery {
            CompanyTable.deleteWhere { companyName eq (name) }
        }
    }

    override suspend fun findCompanyByName(name: String): CompanyModel? {
        return dbQuery {
            val company = CompanyTable.select {
                CompanyTable.companyName eq name
            }
                .map { rowToCompany(row = it) }
                .singleOrNull()
            company
        }
    }

    private fun rowToCompany(row: ResultRow?): CompanyModel? {
        if (row == null) {
            return null
        }
        return CompanyModel(
            id = row[CompanyTable.id],
            companyName = row[CompanyTable.companyName],
            companyAddress = row[CompanyTable.companyAddress],
            companyLongitude = row[CompanyTable.companyLongitude],
            companyLatitude = row[CompanyTable.companyLatitude],
            companyCreatedDate = row[CompanyTable.companyCreatedDate]
        )

    }
}