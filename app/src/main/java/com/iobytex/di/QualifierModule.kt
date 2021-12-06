package com.iobytex.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NetworkMoshi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TaskModelMoshi