package com.example.ykiosk_android_test.bluetooth

import android.bluetooth.BluetoothManager
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrintControlScreen(
    deviceName: String,
    deviceAddress: String,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val bluetoothManager = remember {context.getSystemService(Context.BLUETOOTH_SERVICE)} as BluetoothManager
    val printerManager = remember { BluetoothPrinterManager(bluetoothManager.adapter) }

    var connectionStatus by remember { mutableStateOf("연결중") }
    var isConnected by remember {mutableStateOf(false)}

    //화면 진입시 자동으로 연결 시도
    LaunchedEffect(deviceAddress) {
        val success = printerManager.connect(deviceAddress)
        if (success) {
            connectionStatus = "연결 성공: $deviceName"
            isConnected = true
        } else {
            connectionStatus = "연결 실패. 프린터를 확인해주세요."
            isConnected = false
        }
    }

    // 화면을 나갈 떄 소켓 닫기
    DisposableEffect(Unit) {
        onDispose {
            printerManager.closeConnection()
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(title = { Text("프린터 제어") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = connectionStatus, style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(24.dp))

            val scope = rememberCoroutineScope() // 버튼 클릭 시 코루틴 실행을 위해 필요

            if (isConnected) {
                Button(
                    onClick = {
                        scope.launch {
                            val success = printerManager.printReceiptTestBitmapMode()
                            if (!success) {
                                // 실패 시 토스트 알림 등
                                print("출력 실패")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(60.dp)
                ) {
                    Text("테스트 영수증 출력하기")
                }
            }

            Button(onClick = onBack, modifier = Modifier.padding(top = 16.dp)) {
                Text("뒤로 가기")
            }
        }




    }


}