package com.example.comparison.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private var instance: Retrofit? = null

    private const val BASE_URL = "https://port-0-node-3a9t2ble0n86ua.sel3.cloudtype.app/"

    // SingleTon - 인스턴스 재생성 방지를 위한 싱글톤 패턴 사용
    fun getInstance(): Retrofit {
        if (instance == null) {

            // interceptor 설정
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

            instance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        return instance!!
    }




}