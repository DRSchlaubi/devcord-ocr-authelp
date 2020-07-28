package tech.devcord.autohelp.ocr.core

import io.ktor.application.ApplicationCallPipeline
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.Route
import tech.devcord.autohelp.ocr.ratelimit.Ratelimiter

internal inline fun Route.ratelimited(callback: Route.() -> Unit) = with(callback) {
    intercept(ApplicationCallPipeline.Call) {
        if (Ratelimiter.isAvailable) return@intercept proceed()
        context.respond(HttpStatusCode.TooManyRequests)
        finish()
    }
}
