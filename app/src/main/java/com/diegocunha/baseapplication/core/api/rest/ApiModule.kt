package com.diegocunha.baseapplication.core.api.rest

import com.diegocunha.baseapplication.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.scope.Scope
import org.koin.dsl.module

val apiModule = module {
    factory {
        okHttp3()
    }

    single {
        Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }
}

private fun Scope.okHttp3(): OkHttpClient {
    val builder = OkHttpClient.Builder()
    val level = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor.Level.BODY
    } else {
        HttpLoggingInterceptor.Level.NONE
    }

    val logger = HttpLoggingInterceptor()
    logger.level = level

    builder.interceptors().add(logger)
    return builder.build()
}