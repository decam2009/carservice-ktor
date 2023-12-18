package com.boriskaloshin.domain.repository

import com.boriskaloshin.data.model.UserModel

interface UserRepository {
    suspend fun getUserByEmail (email: String): UserModel?

    suspend fun insertUser (user: UserModel)
}