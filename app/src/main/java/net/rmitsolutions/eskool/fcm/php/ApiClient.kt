package net.rmitsolutions.eskool.fcm.php

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by RMIT on 26/10/2017.
 */
class ApiClient {

    val BASE_URL = "http://127.0.2.2/firebase/"
    @PublishedApi
    internal var retrofit: Retrofit
    val CONNECTION_TIMEOUT: Long = 60
    val READ_TIMEOUT: Long = 60

    init {
        val gson = GsonBuilder()
                .setLenient()
                .create()

        val httpClient = OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .build()

        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient)
                .build()
    }

    inline fun <reified T> getService(): T {
        return retrofit.create(T::class.java)
    }
}