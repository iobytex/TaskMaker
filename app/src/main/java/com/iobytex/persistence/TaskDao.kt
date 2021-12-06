package com.iobytex.persistence

import androidx.room.*
import com.iobytex.domain.TaskWithTypes
import com.iobytex.domain.Type
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.OffsetDateTime
import java.util.*

@Dao
interface TaskDao {

    @Transaction
    @Query("SELECT * FROM Task WHERE dead_line < date(:deadLine) AND completed = :completed ORDER BY dead_line ASC")
    fun loadNeglectedTaskByDate(deadLine:OffsetDateTime= OffsetDateTime.now(), completed:Boolean=false) : Flow<List<TaskWithTypes>>

    @Transaction
    @Query("SELECT * FROM Task WHERE completed = :completed ORDER BY dead_line DESC")
    fun loadTaskDoneByDate(completed:Boolean=true) : Flow<List<TaskWithTypes>>

    @Transaction
    @Query("SELECT * FROM Task WHERE dead_line > date(:deadLine) AND completed = :completed ORDER BY dead_line ASC ")
    fun loadUpComingTaskByDate(deadLine:OffsetDateTime= OffsetDateTime.now(),completed:Boolean=false) : Flow<List<TaskWithTypes>>

    @Transaction
    @Query("SELECT * FROM Task WHERE dead_line = date(:deadLine) AND completed = :completed ORDER BY dead_line ASC")
    fun loadTodayTaskByDate(deadLine:OffsetDateTime= OffsetDateTime.now(),completed:Boolean=false) : Flow<List<TaskWithTypes>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewTask(vararg taskWithTypes: TaskWithTypes)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewType(vararg type:Type)

    @Transaction
    @Update
    suspend fun updateTask(taskWithTypes: TaskWithTypes)

    @Query("UPDATE task SET completed = :completed WHERE taskId = :taskId")
    suspend fun updateTaskCompleted(taskId:Long,completed: Boolean)

    @Query("DELETE FROM task WHERE  taskId = :taskId")
    suspend fun deleteTask(taskId: Long)

    @Query("DELETE FROM type WHERE  typeId = :typeId")
    suspend fun deleteType(typeId: Long)
}