package com.boriskaloshin.data.model

import com.boriskaloshin.utils.Constants

enum class RoleModel {
    ADMIN, COMPANY_OWNER, CLIENT
}

fun String.getRoleByString(): RoleModel {
    return when (this) {
        Constants.Role.ADMIN -> RoleModel.ADMIN
        Constants.Role.COMPANY_OWNER -> RoleModel.COMPANY_OWNER
        else -> RoleModel.CLIENT
    }
}

fun RoleModel.getStringByRole(): String {
    return when (this) {
        RoleModel.ADMIN -> Constants.Role.ADMIN
        RoleModel.COMPANY_OWNER -> Constants.Role.COMPANY_OWNER
        else -> Constants.Role.CLIENT
    }
}
