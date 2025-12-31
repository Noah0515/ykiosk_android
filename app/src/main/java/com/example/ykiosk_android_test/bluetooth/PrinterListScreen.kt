package com.example.ykiosk_android_test.bluetooth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun PrinterListScreen(
    onDeviceSelected: (BluetoothDeviceDomain) -> Unit,
    storeId: String
) {
    val context = LocalContext.current

    val pairedDevices = remember { mutableStateListOf<BluetoothDeviceDomain>()}

    LaunchedEffect(Unit) {
        pairedDevices.clear()
        pairedDevices.addAll(getPairedDevices(context))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "연결할 프린터 선택", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        if (pairedDevices.isEmpty()) {
            Text(text = "페어링된 기기가 없습니다.\n설정에서 프린터를 등록해주세요")
        } else {
            LazyColumn {
                items(pairedDevices) { device ->
                    DeviceItem(device) {
                        onDeviceSelected(device)
                    }
                }
            }
        }
    }
}

@Composable
fun DeviceItem(device: BluetoothDeviceDomain, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable{ onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = device.name, style = MaterialTheme.typography.titleMedium)
            Text(text = device.address, style = MaterialTheme.typography.bodySmall)
        }
    }
}