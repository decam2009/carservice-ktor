package com.boriskaloshin.model.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object CarTable : Table() {
    val id: Column<Int> = integer("id").autoIncrement().uniqueIndex()
    val carName: Column<String> = varchar("car_name", length = 75)
    val carNumber: Column<String> = varchar("car_number", length = 12)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(id)
}