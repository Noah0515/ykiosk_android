package com.example.ykiosk_android_test.auth

import android.content.Context
import com.example.ykiosk_android_test.login.TokenManager
import com.example.ykiosk_android_test.service.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.219.106:8080/YKiosk/"

    fun getApiService(context: Context): ApiService {
        val tokenManager = TokenManager(context)

        // 인터셉터를 포함한 OkHttpClient 생성
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenManager))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // 우리만의 클라이언트 연결!
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}