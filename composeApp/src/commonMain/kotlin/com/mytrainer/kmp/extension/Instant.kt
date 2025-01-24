package com.mytrainer.kmp.extension

import com.mytrainer.kmp.extension.FormatStyle.MEDIUM
import com.mytrainer.kmp.extension.FormatStyle.SHORT
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.MonthNames.Companion.ENGLISH_ABBREVIATED
import kotlinx.datetime.format.MonthNames.Companion.ENGLISH_FULL
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime

enum class FormatStyle {
    SHORT, MEDIUM, LONG
}

fun Instant.toFormattedDate(
    formatStyle: FormatStyle = SHORT,
    //locale: Locale = Locale.FRENCH,
    timeZone: TimeZone = TimeZone.currentSystemDefault()
): String {
    val date = Instant.parse(toString()).toLocalDateTime(timeZone)
    return when (formatStyle) {
        SHORT -> {
            val day = date.dayOfMonth
            val month = date.monthNumber
            val year = date.year
            "${day.zeroPrefixed()}/${month.zeroPrefixed()}/$year"
        }
        else -> {
            val format = LocalDateTime.Format {
                dayOfMonth()
                char(' ')
                monthName(if (formatStyle == MEDIUM) ENGLISH_ABBREVIATED else ENGLISH_FULL)
                char(' ')
                year()
            }
            date.format(format)
        }
    }
}

/*fun Instant.toFormattedTime(
    formatStyle: FormatStyle = FormatStyle.SHORT,
    locale: Locale = Locale.FRENCH,
    zoneId: ZoneId = ZoneId.systemDefault()
): String {
    val formatter = when (formatStyle) {
        FormatStyle.SHORT -> DateTimeFormatter.ofPattern("HH'H'mm")
        else -> DateTimeFormatter.ofLocalizedTime(formatStyle)
            .withLocale(locale)
            .withZone(zoneId)
    }.withLocale(locale)
    return formatter.format(LocalDateTime.ofInstant(this, zoneId))
}

fun Instant.toFormattedDateTime(
    formatStyle: FormatStyle = FormatStyle.SHORT,
    locale: Locale = Locale.FRENCH,
    zoneId: ZoneId = ZoneId.systemDefault()
) = "${toFormattedDate(formatStyle, locale, zoneId)} à ${toFormattedTime(FormatStyle.SHORT, locale, zoneId)}"
*/