package com.boriskaloshin.utils

class Constants {
    object Role {
        const val ADMIN = "ADMIN"
        const val COMPANY_OWNER = "COMPANY_OWNER"
        const val CLIENT = "CLIENT"
    }

    object Error {
        const val GENERAL = "Oh shit, something went wrong"
        const val WRONG_EMAIL = "Wrong email address"
        const val INCORRECT_PASSWORD = "Incorrect password"
        const val COMPANY_CREATED_SUCCESSFULLY = "Company created successfully"
        const val COMPANY_DELETED_SUCCESSFULLY = "Company deleted successfully"
        const val COMPANY_NOT_FOUND = "Wrong company name"
    }
}