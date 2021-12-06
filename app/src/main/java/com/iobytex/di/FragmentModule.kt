package com.iobytex.di

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.google.zxing.client.android.BeepManager
import com.google.zxing.integration.android.IntentIntegrator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@InstallIn(FragmentComponent::class)
@Module
object FragmentModule {

    @Provides
    @Singleton
    fun provideIntentIntegrator(fragment: Fragment) : IntentIntegrator {
        return IntentIntegrator(fragment.activity)
    }

    @Provides
    @Singleton
    fun provideDecoratedBarcodeView(fragment: Fragment) : BeepManager {
        return BeepManager(fragment.activity)
    }
}