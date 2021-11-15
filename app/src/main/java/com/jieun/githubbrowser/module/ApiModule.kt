package com.jieun.githubbrowser.module

import com.jieun.githubbrowser.BuildConfig
import com.jieun.githubbrowser.network.ApiService
import com.jieun.githubbrowser.network.NetworkConstants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * date 2021-11-13
 * create by jieun
 */
val apiModule: Module = module {
    single {
        OkHttpClient.Builder()
            .connectTimeout(NetworkConstants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(NetworkConstants.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(NetworkConstants.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(get<Interceptor>())
            .addNetworkInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val builder = original.newBuilder().apply {
                method(original.method, original.body)
            }

            val request = builder
                .addHeader("Accept", NetworkConstants.HEADER_ACCEPT)
//                .addHeader("Authorization", NetworkConstants.HEADER_AUTHORIZATION) // 개인 토큰 주석
                .build()

            try {
                chain.proceed(request)
            } catch (e: Exception) {
                e.printStackTrace()
                chain.proceed(request)
            }
        }
    }

    single {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    single {
        Retrofit.Builder()
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(NetworkConstants.BASE_URL)
            .build()
    }

    single {
        get<Retrofit>().create(
            ApiService::class.java
        )
    }
}
