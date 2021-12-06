package com.iobytex.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iobytex.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CoroutineModule {

    @Provides
    @Singleton
    fun provideCoroutineDispatcher(): CoroutineDispatcher{
        return Dispatchers.IO
    }

    @Provides
    @ViewModelScoped
    fun provideCoroutineScope(viewModel: MainViewModel) : CoroutineScope {
        return viewModel.viewModelScope
    }
}