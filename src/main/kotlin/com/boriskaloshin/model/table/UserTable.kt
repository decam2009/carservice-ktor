package com.boriskaloshin.model.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object UserTable : Table() {

    private val id: Column<Int> = integer("id").autoIncrement().uniqueIndex()
    val userCustomerId: Column<Int> = integer("user_customer_id").references(CustomerTable.id)
    val userPassword: Column<String> = varchar("user_password", length = 255)
    val isUserActive: Column<Boolean> = bool("user_isActive").default(false)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}