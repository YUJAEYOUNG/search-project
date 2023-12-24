package global.search.pretask.common.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateUtils {
    private const val YYYYMMDD_FORMAT = "yyyyMMdd"

    fun to(str: String): String {
        val instant = Instant.parse(str)
        val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

        val formatter = DateTimeFormatter.ofPattern(YYYYMMDD_FORMAT)
        return localDateTime.format(formatter)
    }
}