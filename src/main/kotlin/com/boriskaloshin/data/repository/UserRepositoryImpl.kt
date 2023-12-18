package com.boriskaloshin.data.repository

import com.boriskaloshin.data.model.UserModel
import com.boriskaloshin.data.model.getRoleByString
import com.boriskaloshin.data.model.getStringByRole
import com.boriskaloshin.data.model.table.CustomerTable
import com.boriskaloshin.data.model.table.UserTable
import com.boriskaloshin.domain.repository.UserRepository
import com.boriskaloshin.plugins.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*


class UserRepositoryImpl : UserRepository {


    override suspend fun getUserByEmail(email: String): UserModel? {
        return dbQuery {
            val join = Join(
                CustomerTable,
                UserTable,
                onColumn = CustomerTable.id, otherColumn = UserTable.userCustomerId,
                joinType = JoinType.LEFT,
                additionalConstraint = ({ CustomerTable.customerEmail eq (email) })
            )
            join.selectAll()
                .map { rowToUser(row = it) }
                .singleOrNull()
        }
    }

    override suspend fun insertUser(user: UserModel) {
        return dbQuery {
            CustomerTable.insert { table ->
                table[customerFirstName] = user.firstName
                table[customerSecondName] = user.lastname
                table[customerSurname] = ""
                table[customerEmail] = user.email
                table[customerPhoneNumber] = user.phoneNumber
                table[customerRole] = user.role.getStringByRole()
            }
            UserTable.insert { table ->
                table[userCustomerId] = CustomerTable.slice(CustomerTable.id.max()).selectAll() //myseq.nextIntVal() //Использование
                // последовательности
                table[userPassword] = user.password
                table[isUserActive] = user.isActive
            }
        }
    }


    private fun rowToUser(row: ResultRow?): UserModel? {
        if (row == null) {
            return null
        }

        return UserModel(
            id = row[UserTable.id],
            email = row[CustomerTable.customerEmail],
            login = row[CustomerTable.customerEmail],
            password = row[UserTable.userPassword],
            firstName = row[CustomerTable.customerFirstName],
            lastname = row[CustomerTable.customerSurname],
            phoneNumber = row[CustomerTable.customerPhoneNumber],
            isActive = row[UserTable.isUserActive],
            role = row[CustomerTable.customerRole].getRoleByString()
        )
    }
}