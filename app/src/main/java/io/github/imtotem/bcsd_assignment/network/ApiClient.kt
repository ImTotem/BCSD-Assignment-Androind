package io.github.imtotem.bcsd_assignment.network

import io.github.imtotem.bcsd_assignment.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://api.github.com/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val request = it.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer ${BuildConfig.GITHUB_API_KEY}")
                .build()
            it.proceed(request)
        }
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}