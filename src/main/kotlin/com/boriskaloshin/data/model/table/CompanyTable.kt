package com.boriskaloshin.data.model.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object CompanyTable : Table() {

    private val id: Column<Int> = integer("id").autoIncrement().uniqueIndex()
    val companyName: Column<String> = varchar("company_name", length = 75)
    val companyAddress: Column<String> = varchar("company_address", length = 120)
    val companyLongitude: Column<Float> = float("company_longitude")
    val companyLatitude: Column<Float> = float("company_latitude")
    val companyCreatedDate: Column<String> = varchar("company_created_date", length = 40)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}