package com.example.clickanime.data.remote

import com.example.clickanime.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import timber.log.Timber
import java.util.concurrent.TimeUnit

object NetworkClient {

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues  = true
        encodeDefaults     = true
    }

    private val authInterceptor = okhttp3.Interceptor { chain ->
        val req = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${BuildConfig.TMDB_API_KEY}")
            .addHeader("accept", "application/json")
            .build()
        chain.proceed(req)
    }

    private val loggingInterceptor = HttpLoggingInterceptor { msg ->
        Timber.tag("OkHttp").d(msg)
    }.apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.TMDB_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val tmdbApiService: TmdbApiService = retrofit.create(TmdbApiService::class.java)
}