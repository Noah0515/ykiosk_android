package com.example.ykiosk_android_test.print

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint

object ImageUtils {
    // PT-210 등 58mm 프린터 표준 너비 (384 dots)
    private const val PRINTER_WIDTH_PX = 384

    /**
     * 1. 텍스트를 비트맵 이미지로 그려주는 함수
     * @param text 출력할 내용 (자동 줄바꿈 됨)
     * @param textSize 글자 크기 (픽셀 단위, 기본 24f)
     * @param isBold 굵게 할지 여부
     */
    fun textToBitmap(text: String, textSize: Float = 24f, isBold: Boolean = false): Bitmap {
        val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            this.textSize = textSize
            this.isFakeBoldText = isBold
        }

        // 너비에 맞춰 자동 줄바꿈을 해주는 레이아웃 생성
        val staticLayout = StaticLayout.Builder.obtain(text, 0, text.length, textPaint, PRINTER_WIDTH_PX)
            .setAlignment(Layout.Alignment.ALIGN_NORMAL)
            .setLineSpacing(0f, 1.0f)
            .setIncludePad(false)
            .build()

        // 계산된 높이만큼 비트맵 생성
        val height = staticLayout.height
        val bitmap = Bitmap.createBitmap(PRINTER_WIDTH_PX, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.WHITE) // 배경 흰색
        staticLayout.draw(canvas) // 텍스트 그리기

        return bitmap
    }

    /**
     * 2. 비트맵을 ESC/POS 래스터(점) 데이터로 변환하는 함수
     * (복잡한 바이너리 계산 로직이므로 수정 없이 사용하세요)
     */
    fun decodeBitmapToData(bmp: Bitmap): ByteArray {
        val bmpWidth = bmp.width
        val bmpHeight = bmp.height
        val list = ArrayList<Byte>()
        var bitLen = bmpWidth / 8
        val zeroCount = bmpWidth % 8
        if (zeroCount > 0) {
            bitLen++
        }

        with(list) {
            // ESC/POS 이미지 출력 명령어 헤더 (GS v 0)
            add(0x1D.toByte())
            add(0x76.toByte())
            add(0x30.toByte())
            add(0x00.toByte())
            // 너비 (xL, xH)
            add(bitLen.toByte())
            add(0x00.toByte())
            // 높이 (yL, yH)
            add((bmpHeight % 256).toByte())
            add((bmpHeight / 256).toByte())
        }

        // 픽셀을 돌면서 검은색이면 1, 흰색이면 0으로 비트 패킹
        for (i in 0 until bmpHeight) {
            for (j in 0 until bitLen) {
                var data = 0x00
                for (k in 0..7) {
                    val x = j * 8 + k
                    if (x < bmpWidth) {
                        val color = bmp.getPixel(x, i)
                        // 밝기(Luminance) 계산으로 흑백 판별
                        val luminance = (Color.red(color) * 0.299 + Color.green(color) * 0.587 + Color.blue(color) * 0.114)
                        if (luminance < 128) { // 어두우면(검은색) 비트 1 설정
                            data = data or (0x80 shr k)
                        }
                    }
                }
                list.add(data.toByte())
            }
        }
        return list.toByteArray()
    }
}