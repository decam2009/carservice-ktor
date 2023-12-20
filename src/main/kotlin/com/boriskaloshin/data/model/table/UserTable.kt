package com.boriskaloshin.data.model.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object UserTable : Table() {

    val id: Column<Int> = integer("id").autoIncrement().uniqueIndex()
    val userCustomerId: Column<Int> = integer("user_customer_id")
    val userPassword: Column<String> = varchar("user_password", length = 255)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}