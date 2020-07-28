package tech.devcord.autohelp.ocr.core

import io.ktor.application.ApplicationCallPipeline
import io.ktor.routing.Route
import io.ktor.auth.parseAuthorizationHeader
import io.ktor.http.HttpStatusCode
import io.ktor.http.auth.HttpAuthHeader
import io.ktor.response.respond
import tech.devcord.autohelp.ocr.config.Config

fun Route.authorized(callback: Route.() -> Unit) = with(callback) {
    intercept(ApplicationCallPipeline.Call) {
        val authHeader = context.request.parseAuthorizationHeader() ?: return@intercept context.respond(HttpStatusCode.Unauthorized, "Unauthorized")
        if (authHeader !is HttpAuthHeader.Single) return@intercept context.respond(HttpStatusCode.Unauthorized, "Bad Auth Header")
        val token = authHeader.blob

        if (token !in Config.TOKENS) {
            context.respond(HttpStatusCode.Unauthorized, "Unauthorized")
            return@intercept finish()
        }

        proceed()
    }
}