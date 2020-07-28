package tech.devcord.autohelp.ocr.ratelimit

import java.time.OffsetDateTime

data class Stats(val uses: Long, val lastUpdate: OffsetDateTime)
