package com.example.ykiosk_android_test.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(viewModel: KakaoAuthViewModel = viewModel(), navController: NavHostController) {
    val context = LocalContext.current


    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 카카오 로그인 버튼 (커스텀 버튼 또는 이미지)
        Button(
            onClick = {
                viewModel.handleKakaoLogin(context, navController)
                /*
                viewModel.handleKakaoLogin(context, navController) { accessToken ->
                    // ★ 여기서 우리 스프링 서버로 토큰을 보내는 API를 호출하면 됩니다!
                    Log.d("ServerAuth", "서버로 보낼 토큰: $accessToken")
                }*/
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFEE500)), // 카카오 노란색
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text(text = "카카오 로그인", color = Color.Black)
        }
    }
}