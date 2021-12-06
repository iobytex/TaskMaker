package com.iobytex.option

import android.content.res.Resources
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.iobytex.domain.Result
import com.iobytex.domain.Task
import com.iobytex.domain.TaskWithTypes
import com.iobytex.repository.TaskRepository
import com.iobytex.task.R
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class FillTaskViewModel @AssistedInject constructor(
    private val resources: Resources,
    private val taskRepository: TaskRepository,
    @Assisted private val taskWithTypes: TaskWithTypes
    ) : ViewModel() {

    private val _taskData: MutableStateFlow<Result<Task>> = MutableStateFlow(Result.Loading)
    val taskData: StateFlow<Result<Task>> = _taskData

    private val _colorPalette : MutableStateFlow<Result<List<Palette.Swatch>?>> = MutableStateFlow(Result.Loading)
    val colorPalette: StateFlow<Result<List<Palette.Swatch>?>> = _colorPalette


    init {
        viewModelScope.launch {
            launch {  }
            Palette.from(BitmapFactory.decodeResource(resources, R.drawable.palette)).generate { palette ->

               palette?.swatches.let {
                   _colorPalette.value = Result.Success(it)
               }
            }
        }
    }

    companion object{
        fun provideFactory(
            assistedFactory: FillTaskViewModelFactory,
            taskWithTypes: TaskWithTypes?
        ) : ViewModelProvider.Factory = object :  ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(taskWithTypes) as T
            }

        }
    }

}


@AssistedFactory
interface FillTaskViewModelFactory{
    fun create(taskWithTypes: TaskWithTypes?) : FillTaskViewModel
}