package com.iobytex.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iobytex.domain.Result
import com.iobytex.domain.Task
import com.iobytex.domain.TaskWithTypes
import com.iobytex.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TaskViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {

    val todayTask:  StateFlow<Result<List<TaskWithTypes>>> = taskRepository.loadTodayTask().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        Result.Loading
    )

    val upComingTask :  StateFlow<Result<List<TaskWithTypes>>> = taskRepository.loadUpcomingTask().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        Result.Loading
    )

    val neglectedTask :  StateFlow<Result<List<TaskWithTypes>>> = taskRepository.loadNeglectedTaskByDate().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        Result.Loading
    )

    val taskDone :   StateFlow<Result<List<TaskWithTypes>>> = taskRepository.loadTaskDone().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        Result.Loading
    )

    init {
        viewModelScope.launch {
            //TODO: Get today data


            //TODO: Get upcoming data

            //TODO Get neglected data

            //TODO: Get taskDone data
        }
    }

}
