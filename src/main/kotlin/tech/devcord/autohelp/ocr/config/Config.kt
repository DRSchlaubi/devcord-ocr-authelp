package tech.devcord.autohelp.ocr.config

import ch.qos.logback.classic.Level

/**
 * Environment based config of application.
 */
object Config {


    /**
     * Maxmial amount of requests per mont
     */
    val MAX_REQUESTS =
        System.getenv("MAX_REQUESTS")?.toLongOrNull() ?: error("Missing max requests environment variable")


    /**
     * Logging level
     */
    val LOG_LEVEL: Level = System.getenv("LOG_LEVEL")?.let { Level.valueOf(it) } ?: Level.INFO

    /**
     * List of valid tokens.
     */
    val TOKENS: List<String> = System.getenv("TOKENS")?.split(',') ?: error("Missing tokens environment variable")

}
