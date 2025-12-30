package com.example.ykiosk_android_test.login

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/auth/kakao/android")
    fun loginWithKakao(@Body request: KakaoLoginRequest): Call<AuthResponse>
}