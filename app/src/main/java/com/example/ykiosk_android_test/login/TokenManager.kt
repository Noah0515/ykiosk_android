package com.example.ykiosk_android_test.login

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class TokenManager(context: Context) {
    // MasterKeys 대신 MasterKey 사용 (최신 방식)
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs = EncryptedSharedPreferences.create(
        context,
        "auth_prefs", // 파일 이름
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    // 토큰 저장
    fun saveToken(token: String) {
        prefs.edit().putString("jwt_token", token).apply()
    }

    // 토큰 읽기
    fun getToken(): String? {
        return prefs.getString("jwt_token", null)
    }

    // 로그아웃 시 토큰 삭제
    fun clearToken() {
        prefs.edit().remove("jwt_token").apply()
    }
}