package com.boriskaloshin.model.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object CustomerTable : Table() {
    val id: Column<Int> = integer("id").autoIncrement().uniqueIndex()
    val customerFirstName: Column<String> = varchar("customer_first_name", length = 75)
    val customerSecondName: Column<String> = varchar("customer_second_name", length = 75)
    val customerSurname: Column<String> = varchar("customer_surname", length = 75)
    val customerEmail: Column<String> = varchar("customer_email", length = 75)
    val customerPhoneNumber: Column<String> = varchar("customer_phone_number", length = 75)
    val customerCarId: Column<Int> = integer("customer_car_id").uniqueIndex().references(CarTable.id)
    val customerRole: Column<Int> = integer("customer_role")

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}