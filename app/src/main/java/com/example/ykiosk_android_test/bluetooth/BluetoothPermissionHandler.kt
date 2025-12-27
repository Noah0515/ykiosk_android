package com.example.ykiosk_android_test.bluetooth

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember

@Composable
fun BluetoothPermissionHandler(
    onPermissionsGranted: () -> Unit,
    onPermissionsDenied: () -> Unit
) {
    val permissions = remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            //안드로이드 12(api 31) 이상: 근처 기기 스캔 및 연결 권한
            arrayOf(
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT
            )
        } else {
            // 안드로이드 11 미만이면 위치권한 필요
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN
            )
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        val allGranted = result.values.all {it}
        if (allGranted) {
            onPermissionsGranted()
        } else {
            onPermissionsDenied()
        }

    }

    LaunchedEffect(Unit) {
        launcher.launch(permissions)
    }
}