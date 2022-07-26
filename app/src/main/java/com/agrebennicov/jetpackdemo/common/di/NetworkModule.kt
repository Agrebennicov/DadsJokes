package com.agrebennicov.jetpackdemo.common.di

import com.agrebennicov.jetpackdemo.BuildConfig
import com.agrebennicov.jetpackdemo.features.random.repository.RandomService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://icanhazdadjoke.com/"
private const val GIT_LINK = "https://github.com/Agrebennicov/DadsJokes"
private const val USER_AGENT = "User-Agent"
private const val CONTENT_TYPE = "Accept"
private const val CONTENT_TYPE_VALUE = "application/json"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        val level = if (BuildConfig.DEBUG) BODY else NONE
        logging.setLevel(level)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addNetworkInterceptor { chain ->
                chain.proceed(
                    chain.request()
                        .newBuilder()
                        .header(USER_AGENT, GIT_LINK)
                        .header(CONTENT_TYPE, CONTENT_TYPE_VALUE)
                        .build()
                )
            }
            .build()

    }

    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideRandomService(retrofit: Retrofit): RandomService =
        retrofit.create(RandomService::class.java)
}