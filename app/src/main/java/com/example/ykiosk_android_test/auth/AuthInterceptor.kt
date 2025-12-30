package com.example.ykiosk_android_test.auth

import android.util.Log
import com.example.ykiosk_android_test.login.TokenManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val tokenManager: TokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenManager.getToken()
        val request = chain.request().newBuilder()

        Log.d("AuthCheck", "인터셉터에서 확인한 토큰: [$token]")
        // 토큰이 있다면 헤더에 추가
        if (token != null) {
            request.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(request.build())
    }
}