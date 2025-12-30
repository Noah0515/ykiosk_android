package com.example.ykiosk_android_test

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this,"9a2e5da9a2345081c22bd959547420eb")
    }
}