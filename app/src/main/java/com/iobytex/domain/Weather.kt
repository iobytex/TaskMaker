package com.iobytex.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import org.threeten.bp.OffsetDateTime

@Entity(tableName = "weather")
@Parcelize
@JsonClass(generateAdapter = true)
data class Weather(
    @PrimaryKey
    val id: Int?,
    val description: String?,
    val icon: String?,
    val main: String?,
    val createdAt: OffsetDateTime? = OffsetDateTime.now()
) : Parcelable
