package com.iobytex.option

import androidx.lifecycle.ViewModel
import com.iobytex.domain.OptionTask
import com.iobytex.domain.Result
import com.iobytex.repository.TaskRepository
import com.iobytex.task.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class TaskOptionViewModel @Inject constructor(private val taskRepository: TaskRepository) : ViewModel() {

    private  val _inputTaskType = MutableStateFlow<Result<List<OptionTask>>>(Result.Loading)
    val optionTaskType: StateFlow<Result<List<OptionTask>>> = _inputTaskType

    init {
        _inputTaskType.value = Result.Success(listOf(
                OptionTask(0,"Fill", R.drawable.ic_edit_square_2),
                OptionTask(1,"Scan",R.drawable.ic_scan)
            )
        )
    }


}

