package com.iobytex.domain

import java.lang.Exception

sealed class Result<out T>{
    object Loading : Result<Nothing>()
    data class Success<out T>(val value: T): Result<T>()
    data class Error(val exception: Exception):Result<Nothing>()

    override fun toString(): String {
        return when(this){
            is Success<*>-> "Success[value=${value}]"
            is Error -> "Error[exception=${exception}]"
            is Loading -> "Loading"
        }
    }
}

