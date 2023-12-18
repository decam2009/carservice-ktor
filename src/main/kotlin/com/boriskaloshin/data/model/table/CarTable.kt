package com.boriskaloshin.data.model.table

import com.boriskaloshin.data.model.table.CustomerTable.references
import com.boriskaloshin.data.model.table.CustomerTable.uniqueIndex
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object CarTable : Table() {
    val id: Column<Int> = integer("id").autoIncrement().uniqueIndex()
    val carName: Column<String> = varchar("car_name", length = 75)
    val carNumber: Column<String> = varchar("car_number", length = 12)
    val customerId: Column<Int> = integer("customer_car_id").references(CustomerTable.id)


    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}