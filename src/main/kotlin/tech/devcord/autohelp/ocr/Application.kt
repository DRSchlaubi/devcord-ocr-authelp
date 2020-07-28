package tech.devcord.autohelp.ocr

import ch.qos.logback.classic.Logger
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.ktor.response.respondRedirect
import io.ktor.routing.route
import io.ktor.routing.routing
import mu.KotlinLogging
import org.slf4j.LoggerFactory
import org.slf4j.event.Level
import tech.devcord.autohelp.ocr.config.Config
import tech.devcord.autohelp.ocr.core.ocr
import tech.devcord.autohelp.ocr.ratelimit.Ratelimiter

private val LOG = KotlinLogging.logger { }

/**
 * Program entry point
 */
fun main(args: Array<String>) {

    val rootLogger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME) as Logger
    rootLogger.level = Config.LOG_LEVEL

    io.ktor.server.netty.EngineMain.main(args)
}

/**
 * KTor module
 */
@Suppress("unused", "UNUSED_PARAMETER") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    LOG.info { "Available: ${Ratelimiter.isAvailable}" }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    install(CallLogging) {
        level = Level.DEBUG
    }

    routing {
        route("/") {
            handle {
                context.respondRedirect("https://github.com/DRSchlaubi/devcord-ocr-authelp", permanent = true)
            }
        }
        ocr()
    }
}
