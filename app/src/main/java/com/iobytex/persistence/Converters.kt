package com.iobytex.persistence

import androidx.room.TypeConverter
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

class Converters {

    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME


    @TypeConverter
    fun toOffsetDateTime(date: OffsetDateTime?): String? {
        return date?.format(formatter)
    }

    @TypeConverter
    fun fromOffsetDateTime(value: String?): OffsetDateTime? {
        return value?.let { formatter.parse(it, OffsetDateTime::from) }
    }

}