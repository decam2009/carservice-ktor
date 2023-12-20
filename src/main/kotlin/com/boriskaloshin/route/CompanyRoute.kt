package com.boriskaloshin.route

import com.boriskaloshin.data.model.CompanyModel
import com.boriskaloshin.data.model.request.company.CompanyRequest
import com.boriskaloshin.data.model.responce.BaseResponce
import com.boriskaloshin.domain.usecase.CompanyUseCase
import com.boriskaloshin.utils.Constants
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.CompanyRoute(companyUseCase: CompanyUseCase) {
    authenticate("jwt") {
        post("api/v1/addcompany") {
            val companyRequest = call.receiveNullable<CompanyRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, BaseResponce(false, Constants.Error.GENERAL))
                return@post
            }
            try {
                val company = CompanyModel(
                    id = 0,
                    companyName = companyRequest.companyName,
                    companyAddress = companyRequest.companyAddress,
                    companyLongitude = companyRequest.companyLongitude,
                    companyLatitude = companyRequest.companyLatitude,
                    companyCreatedDate = companyRequest.companyCreatedDate
                )
                companyUseCase.createCompany(company)
                call.respond(HttpStatusCode.OK, BaseResponce(true, Constants.Error.COMPANY_CREATED_SUCCESSFULLY))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, BaseResponce(false, e.message ?: Constants.Error.GENERAL))
            }
        }

        delete("api/v1/delcompany") {
            val companyRequest = call.receiveNullable<CompanyRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, BaseResponce(false, Constants.Error.GENERAL))
                return@delete
            }
            try {
                companyUseCase.deleteCompany(name = companyRequest.companyName)
                call.respond(HttpStatusCode.OK, BaseResponce(true, Constants.Error.COMPANY_DELETED_SUCCESSFULLY))
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, e.message ?: Constants.Error.GENERAL)
            }
        }

        get("api/v1/getcompany/{name?}") {
            val name = call.parameters["name"] ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest, BaseResponce(false, Constants.Error.GENERAL))
                return@get
            }


            try {
                val company = companyUseCase.findCompanyByName(name = name)
                if (company == null) {
                    call.respond(HttpStatusCode.Conflict, BaseResponce(false, Constants.Error.COMPANY_NOT_FOUND))
                } else {
                    call.respond(HttpStatusCode.OK, company)
                }
            } catch (e: Exception) {
                call.respond(HttpStatusCode.Conflict, e.message ?: Constants.Error.GENERAL)
            }
        }
    }
}