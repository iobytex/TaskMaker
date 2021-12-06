package com.iobytex.di

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.iobytex.task.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @ActivityScoped
    @Singleton
    fun provideNavController(activity: Activity): NavController {
        return activity.findNavController(R.id.nav_host_fragment)
    }

    @Provides
    @Singleton
    fun provideAppBarConfig(navController: NavController): AppBarConfiguration {
        return AppBarConfiguration(navController.graph)
    }

    @Provides
    @Singleton
    fun provideResource(@ApplicationContext context:Context) : Resources {
        return context.applicationContext.resources
    }
}