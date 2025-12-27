package com.example.ykiosk_android_test.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context

@SuppressLint("MissingPermission")
fun getPairedDevices(context: Context): List<BluetoothDeviceDomain> {
    val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter

    if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled) {
        return emptyList()
    }

    return bluetoothAdapter.bondedDevices.map { device ->
        BluetoothDeviceDomain(
            name = device.name ?: "unknown Device",
            address = device.address
        )
    }
}