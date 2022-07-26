package com.agrebennicov.jetpackdemo.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {

    @Provides
    @IO
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Main
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}
