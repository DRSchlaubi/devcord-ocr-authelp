package tech.devcord.autohelp.ocr.ratelimit

import tech.devcord.autohelp.ocr.config.Config
import tech.devcord.autohelp.ocr.util.deserialize
import tech.devcord.autohelp.ocr.util.toJson
import java.nio.file.Files
import java.nio.file.Path
import java.time.OffsetDateTime

object Ratelimiter {

    val isAvailable: Boolean
        get() = stats.uses < Config.MAX_REQUESTS

    private var stats: Stats
    private val file = Path.of("usages.json")

    init {
        stats = if (!Files.exists(file)) {
            Files.createFile(file)
            val newStats = Stats(0, OffsetDateTime.now())
            newStats.save()
            newStats // return
        } else {
            Files.readAllLines(file).joinToString("\n").deserialize() // return <T = Stats>
        }
    }

    fun register() {
        stats = stats.copy(uses = stats.uses + 1)
        stats.save()
    }

    private fun Stats.save() = Files.newBufferedWriter(file).use {
        it.write(toJson())
    }
}