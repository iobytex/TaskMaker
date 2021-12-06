package com.iobytex.di

import com.iobytex.network.HttpInterceptor
import com.iobytex.network.WeatherClient
import com.iobytex.network.WeatherService
import com.iobytex.utils.Utils
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient{

        return OkHttpClient.Builder()
            .addInterceptor(HttpInterceptor())
            .build()

    }

    @NetworkMoshi
    @Provides
    @Singleton
    fun provideMoshi():Moshi{
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }





    @Provides
    @Singleton
    fun provideMoshiConverter( @NetworkMoshi moshi: Moshi):MoshiConverterFactory{
        return MoshiConverterFactory.create(moshi)
    }


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient,moshiConverterFactory: MoshiConverterFactory):Retrofit{
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("")
            .addConverterFactory(moshiConverterFactory)
            .build()
    }



    @Provides
    @Singleton
    fun provideWeatherService(retrofit: Retrofit): WeatherService{
        return retrofit.create(WeatherService::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherClient(weatherService: WeatherService): WeatherClient{
        return WeatherClient(weatherService)
    }
}