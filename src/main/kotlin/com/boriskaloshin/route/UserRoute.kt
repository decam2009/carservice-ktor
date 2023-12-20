package com.boriskaloshin.route

import com.boriskaloshin.auth.hash
import com.boriskaloshin.data.model.UserModel
import com.boriskaloshin.data.model.getRoleByString
import com.boriskaloshin.data.model.request.SignUpRequest
import com.boriskaloshin.data.model.request.SigninRequest
import com.boriskaloshin.data.model.responce.BaseResponce
import com.boriskaloshin.domain.usecase.UserUseCase
import com.boriskaloshin.utils.Constants
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.UserRoute(userUseCase: UserUseCase) {


    post("/api/v1/signup") {
        val signupRequest = call.receiveNullable<SignUpRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, BaseResponce(false, Constants.Error.GENERAL))
            return@post
        }
        try {
            val user = UserModel(
                id = 0,
                email = signupRequest.email.trim().lowercase(),
                login = signupRequest.login.trim().lowercase(),
                password = hash(signupRequest.password.trim()),
                firstName = signupRequest.firstName.trim(),
                lastname = signupRequest.lastname.trim(),
                phoneNumber = signupRequest.phoneNumber.trim(),
                role = signupRequest.role.getRoleByString()
            )
            userUseCase.createUser(user)
            call.respond(HttpStatusCode.OK, BaseResponce(true, userUseCase.generateToken(user)))
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, BaseResponce(false, e.message ?: Constants.Error.GENERAL))
        }
    }

    post("api/v1/signin") {
        val signinRequest = call.receiveNullable<SigninRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, BaseResponce(false, Constants.Error.GENERAL))
            return@post
        }
        try {
            val user = userUseCase.findUserByEmail(signinRequest.email.trim().lowercase())
            if (user == null) {
                call.respond(HttpStatusCode.BadRequest, BaseResponce(false, Constants.Error.WRONG_EMAIL))
            } else {
                if (user.password == hash(signinRequest.password)) {
                    call.respond(HttpStatusCode.OK, BaseResponce(true, userUseCase.generateToken(user = user)))
                } else {
                    call.respond(HttpStatusCode.BadRequest, BaseResponce(false, Constants.Error.INCORRECT_PASSWORD))
                }
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, BaseResponce(false, e.message ?: Constants.Error.GENERAL))
        }
    }
}