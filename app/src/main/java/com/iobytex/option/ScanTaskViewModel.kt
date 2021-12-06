package com.iobytex.option

import androidx.lifecycle.ViewModel
import com.iobytex.domain.Result
import com.iobytex.domain.Task
import com.iobytex.domain.TaskWithTypes
import com.iobytex.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ScanTaskViewModel @Inject constructor(private val taskRepository: TaskRepository): ViewModel() {

    private val _scanResult: MutableStateFlow<Result<TaskWithTypes>> =  MutableStateFlow<Result<TaskWithTypes>>(Result.Loading)
    val scanResult: StateFlow<Result<TaskWithTypes>>  = _scanResult

    fun decodeScan(value: String) : Unit {
        taskRepository.decodeScanResult(value)?.let {
            _scanResult.value = it
        }
    }
}