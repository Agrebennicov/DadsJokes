package com.agrebennicov.jetpackdemo.common.di

import android.content.Context
import androidx.room.Room
import com.agrebennicov.jetpackdemo.common.database.JOKES_DATABASE
import com.agrebennicov.jetpackdemo.common.database.JokesDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideJokeDb(
        @ApplicationContext
        context: Context
    ) = Room.databaseBuilder(
        context,
        JokesDb::class.java,
        JOKES_DATABASE
    )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideJokeDao(
        jokeDb: JokesDb
    ) = jokeDb.jokeDao()
}
