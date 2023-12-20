package com.boriskaloshin.data.model.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object CustomerTable : Table() {
    val id: Column<Int> = integer("id").autoIncrement().uniqueIndex()
    val customerFirstName: Column<String> = varchar("customer_first_name", length = 75)
    val customerSecondName: Column<String> = varchar("customer_second_name", length = 75)
    val customerSurname: Column<String> = varchar("customer_surname", length = 75)
    val customerEmail: Column<String> = varchar("customer_email", length = 75).uniqueIndex()
    val customerPhoneNumber: Column<String> = varchar("customer_phone_number", length = 75)
    val customerRole: Column<String> = varchar("customer_role", length = 8)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}