package com.iobytex.repository

import com.iobytex.domain.Result
import com.iobytex.domain.Task
import com.iobytex.domain.TaskWithTypes
import com.iobytex.domain.Type
import com.iobytex.persistence.TaskDao
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class TaskRepository @Inject constructor(private  val moshi: Moshi,private val taskDao: TaskDao, private val ioDispatcher: CoroutineDispatcher) : Repository {

    fun loadTodayTask() : Flow<Result<List<TaskWithTypes>>> = taskDao.loadTodayTaskByDate()
        .map { task ->
            Result.Success(task)
        }
        .flowOn(ioDispatcher)

    fun loadUpcomingTask() : Flow<Result<List<TaskWithTypes>>> = taskDao.loadUpComingTaskByDate()
        .map { task ->
            Result.Success(task)
        }
        .flowOn(ioDispatcher)

    fun loadTaskDone() : Flow<Result<List<TaskWithTypes>>> = taskDao.loadTaskDoneByDate()
        .map { task ->
            Result.Success(task)
        }
        .flowOn(ioDispatcher)

    fun loadNeglectedTaskByDate() : Flow<Result<List<TaskWithTypes>>> = taskDao.loadNeglectedTaskByDate()
        .map { task ->
            Result.Success(task)
        }
        .flowOn(ioDispatcher)

    suspend  fun addNewTask(taskWithTypes: TaskWithTypes){
        withContext(ioDispatcher){
            taskDao.insertNewTask(taskWithTypes)
        }
    }

    suspend fun updateTask(taskWithTypes: TaskWithTypes){
        withContext(ioDispatcher){
            taskDao.updateTask(taskWithTypes)
        }
    }

    suspend fun insertType(type: Array<Type>){
        withContext(ioDispatcher){
            taskDao.insertNewType(*type)
        }
    }

    suspend fun updateTaskCompleted(taskId: Long, completed: Boolean){
        withContext(ioDispatcher){
            taskDao.updateTaskCompleted(taskId,completed)
        }
    }

    fun decodeScanResult(value: String) : Result<TaskWithTypes>?{
        return try {
            value.let {
                //TODO: convert json result from the code to task object
                val taskJsonAdapter = moshi.adapter<TaskWithTypes>()
                val taskObjResponse = taskJsonAdapter.fromJson(it)
                Timber.d(value)
                Timber.d(taskObjResponse?.task?.title)
                taskObjResponse?.let { objValue ->
                    Result.Success(objValue)
                }
            }
        }catch (e:Exception){
            Result.Error(e)
        }
    }
}