package com.agrebennicov.jetpackdemo.features.welcome.di

import com.agrebennicov.jetpackdemo.features.welcome.repo.WelcomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class WelcomeModule {

    @Provides
    fun provideWelcomeRepository() = WelcomeRepository()
}