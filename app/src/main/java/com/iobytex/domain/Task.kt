package com.iobytex.domain

import android.os.Parcelable
import androidx.room.*
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import kotlinx.parcelize.Parcelize
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

@Entity(tableName = "task")
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true)
    val taskId: Long,
    val title: String,
    val color: Int,
    @ColumnInfo(name = "dead_line")
    val deadLine: OffsetDateTime,
    val location: String,
    val file: String,
    val completed: Boolean,
    val code: String
) : Parcelable


@Entity(tableName = "type")
@Parcelize
data class Type(
    @PrimaryKey(autoGenerate = true) val typeId: Long,
    val title:String,
) : Parcelable


@Entity(primaryKeys = ["taskId", "typeId"])
data class TaskTypeCrossRef(
    val taskId: Long,
    val typeId:Long
)

@Parcelize
data class TaskWithTypes(
    @Embedded val task: Task,
    @Relation(
        parentColumn = "taskId",
        entityColumn = "typeId",
        associateBy = Junction(TaskTypeCrossRef::class)
    )
    val type: List<Type>
) : Parcelable

class TaskModelAdapter{

    private val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    @ToJson
    fun toJson(deadLine: OffsetDateTime?):String?{
        return deadLine?.format(formatter)
    }

    @FromJson
    fun fromJson(value: String?) :OffsetDateTime?{
        return  value?.let {  formatter.parse(it,OffsetDateTime::from) }
    }
}