package com.example.ykiosk_android_test.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class KakaoAuthViewModel : ViewModel() {
    private val TAG = "KakaoLogin"

    //fun handleKakaoLogin(context: Context, onSuccess: (String) -> Unit) {
    fun handleKakaoLogin(context: Context, navController: NavHostController) {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (token != null) {
                // [추가] 우리 서버로 토큰 전송
                sendTokenToServer(token.accessToken, navController, context)
            }

            if (error != null) {
                Log.e(TAG, "카카오 로그인 실패", error)
            } else if (token != null) {
                Log.i(TAG, "카카오 로그인 성공: ${token.accessToken}")
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context, callback = callback)
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

    private fun sendTokenToServer(
        kakaoToken: String,
        navController: NavHostController,
        context: Context) {
        Log.d("ServerAuth", "서버로 요청 시작... 토큰: $kakaoToken")

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.219.106:8080/YKiosk/") // 에뮬레이터에서 내 컴퓨터 로컬 서버 주소
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(AuthService::class.java)

        service.loginWithKakao(KakaoLoginRequest(kakaoToken)).enqueue(object :
            Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    val ourToken = response.body()?.accessToken
                    Log.d("ServerAuth", "우리 서버 JWT 발급 성공: $ourToken")
                    if (ourToken != null) {
                        // 1. [핵심] 먼저 기기에 토큰을 저장합니다.
                        val tokenManager = TokenManager(context)
                        tokenManager.saveToken(ourToken)
                        Log.d("ServerAuth", "기기에 토큰 저장 완료!")

                        // 2. 그 다음에 화면을 이동합니다.
                        navController.navigate("store_main_screen") {
                            popUpTo("login_screen") { inclusive = true }
                        }
                    }

                    /*
                    // ★ 서버 인증까지 완료된 후 페이지 이동!
                    navController.navigate("store_main_screen") {
                        popUpTo("login_screen") { inclusive = true }
                    }*/
                }
            }
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.e("ServerAuth", "서버 통신 실패: ${t.message}")
            }
        })
    }
}