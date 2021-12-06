package com.iobytex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.iobytex.domain.Result
import com.iobytex.domain.Weather
import com.iobytex.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val weatherRepository: WeatherRepository) : ViewModel() {


    val weatherType : StateFlow<Result<Weather>> = weatherRepository.loadCurrentWeather().stateIn(
        scope = viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        Result.Loading
    )


//    fun loadWeather(){
//        viewModelScope.launch{
//            weatherRepository.loadCurrentWeather().collect {
//                _weatherType.value = Result.Success(it)
//            }
//        }
//    }

    suspend fun getGeoLocation(lat:Double, lon:Double){
        withContext(Dispatchers.IO){
            weatherRepository.fetchCurrentWeather(lat, lon)

        }
    }
}