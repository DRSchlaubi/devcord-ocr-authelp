package tech.devcord.autohelp.ocr.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

/**
 * Object mapper used internally.
 */
val objectMapper: ObjectMapper = jacksonObjectMapper().registerKotlinModule().registerModule(JavaTimeModule())

/**
 * Converts this object to json.
 */
fun Any.toJson(): String = objectMapper.writeValueAsString(this)

/**
 * Converts this object to json.
 */
fun Any.toJsonBytes(): ByteArray = objectMapper.writeValueAsBytes(this)

/**
 * Deserializes any json string to [T].
 */
inline fun <reified T : Any> String.deserialize(): T = objectMapper.readValue(this)
