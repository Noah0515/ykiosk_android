package com.example.ykiosk_android_test.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.graphics.Bitmap
import com.example.ykiosk_android_test.print.EscPosCommands
import com.example.ykiosk_android_test.print.ImageUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.UUID

class BluetoothPrinterManager(private val bluetoothAdapter: BluetoothAdapter?) {
    private val SPP_UUID: UUID = UUID.fromString("00001101-0000-1000-0000-00805f9b34fb")
    private var bluetoothSocket: BluetoothSocket? = null

    @SuppressLint("MissingPermission")
    suspend fun connect(address: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val device: BluetoothDevice? = bluetoothAdapter?.getRemoteDevice(address)
            closeConnection()

            bluetoothAdapter?.cancelDiscovery()

            // 1. 먼저 표준 방식으로 시도
            println("Bluetooth Debug: 표준 방식으로 연결 시도...")
            bluetoothSocket = device?.createRfcommSocketToServiceRecord(SPP_UUID)

            try {
                bluetoothSocket?.connect()
                println("Bluetooth Debug: 표준 방식 연결 성공!")
                return@withContext true
            } catch (e: Exception) {
                println("Bluetooth Debug: 표준 방식 실패, Fallback 방식으로 재시도...")

                // 2. 표준 방식 실패 시 'Fallback' 방식으로 재시도 (반사/Reflection 이용)
                // 일부 프린터는 이 방식(채널 1번 강제 점유)으로만 연결됩니다.
                bluetoothSocket = device?.javaClass?.getMethod("createRfcommSocket", Int::class.javaPrimitiveType)
                    ?.invoke(device, 1) as BluetoothSocket?

                bluetoothSocket?.connect()
                println("Bluetooth Debug: Fallback 방식 연결 성공!")
                return@withContext true
            }
        } catch (e: Exception) {
            println("Bluetooth Debug: 모든 연결 시도 실패 -> ${e.message}")
            closeConnection()
            false
        }
    }

    fun closeConnection() {
        try {
            bluetoothSocket?.close()
            bluetoothSocket = null
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // 데이터를 내보내는 스트림 가져오기
    fun getOutputStream() = bluetoothSocket?.outputStream
    suspend fun sendData(data: ByteArray): Boolean = withContext(Dispatchers.IO) {
        try {
            val out = getOutputStream()
            if (out != null) {
                out.write(data)
                out.flush()
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    // [새로 추가] 비트맵 이미지를 전송하는 함수
    suspend fun printImage(bitmap: Bitmap): Boolean = withContext(Dispatchers.IO) {
        try {
            // 1. 비트맵을 프린터 명령어로 변환
            val commandData = ImageUtils.decodeBitmapToData(bitmap)
            // 2. 전송 (기존 sendData 활용)
            sendData(commandData)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // [수정] 기존 테스트 함수를 이미지 방식으로 변경
    suspend fun printReceiptTestBitmapMode(): Boolean {
        return try {
            // 1. 초기화 및 중앙 정렬
            sendData(EscPosCommands.RESET)
            sendData(EscPosCommands.ALIGN_CENTER)

            // 2. 제목 그리기 (크고 굵게)
            val titleBitmap = ImageUtils.textToBitmap("이미지 방식 영수증\n", textSize = 40f, isBold = true)
            printImage(titleBitmap)

            // 3. 내용 그리기 (일반 크기)
            val contentText = """
                --------------------------------
                상품명          단가    수량
                --------------------------------
                아메리카노      4,500     1
                카페라떼        5,000     1
                --------------------------------
                합계: 9,500원
                
                PT-210 한글 완벽 출력 성공!
            """.trimIndent()

            // 왼쪽 정렬로 내용 그리기
            sendData(EscPosCommands.ALIGN_LEFT)
            val contentBitmap = ImageUtils.textToBitmap(contentText, textSize = 24f)
            printImage(contentBitmap)

            // 4. 마무리 피드 및 커팅
            sendData(EscPosCommands.FEED_PAPER)
            // sendData(EscPosCommands.PAPER_CUT) // 커팅기가 있다면 주석 해제

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // 💡 한글 인코딩 핵심 함수
    private suspend fun printKoreanText(text: String) {
        try {
            // 안드로이드 String을 EUC-KR 바이트 배열로 변환 (중요!)
            val bytes = text.toByteArray(charset("euc-kr"))
            sendData(bytes)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}

