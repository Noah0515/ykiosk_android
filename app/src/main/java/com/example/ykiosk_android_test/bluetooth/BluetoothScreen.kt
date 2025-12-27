package com.example.ykiosk_android_test.bluetooth

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun PrinterConnectScreen() {
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val isGranted = permissions.values.all {it}
        if (isGranted) {
            // 권한 허용됨
            //startBluetoothPrinterConnection(context)
        } else {
            // 권한 거부됨
        }

    }

    Button(onClick = {
        // 2. 버튼 클릭 시 OS 버전에 맞는 권한 리스트를 가져와서 요청
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            arrayOf(Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT)
        } else {
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        permissionLauncher.launch(permissions)
    }) {
        Text("영수증 프린터 연결")
    }
}