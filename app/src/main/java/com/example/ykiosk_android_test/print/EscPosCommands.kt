package com.example.ykiosk_android_test.print

object EscPosCommands {
    // 프린터 초기화
    val RESET = byteArrayOf(0x1B, 0x40) // 필수: 모든 설정 초기화
    val KOREAN_FS_MODE = byteArrayOf(0x1C, 0x26) // 한글/한자 모드 활성화 (필수)

    // 💡 0x12 대신 0x15(10진수 21)를 사용하세요.
    val KOREAN_CODE_PAGE = byteArrayOf(0x1B, 0x74, 0x15)
    /*
    byteArrayOf(0x1B, 0x74, 0x0D)
    byteArrayOf(0x1B, 0x74, 0x15)
    byteArrayOf(0x1B, 0x74, 0x1A)
    byteArrayOf(0x1B, 0x74, 0x64)
     */
    //val KOREAN_CODE_PAGE = byteArrayOf(0x1B, 0x74, 0x0D)
    //val KOREAN_CODE_PAGE_ALT = byteArrayOf(0x1B, 0x74, 0x60)
    val ALIGN_LEFT = byteArrayOf(0x1B, 0x61, 0x00)
    val ALIGN_CENTER = byteArrayOf(0x1B, 0x61, 0x01)
    val ALIGN_RIGHT = byteArrayOf(0x1B, 0x61, 0x02)

    // 글자 크기/굵기
    val TEXT_BOLD_ON = byteArrayOf(0x1B, 0x45, 0x01)
    val TEXT_BOLD_OFF = byteArrayOf(0x1B, 0x45, 0x00)
    val TEXT_SIZE_NORMAL = byteArrayOf(0x1D, 0x21, 0x00)
    val TEXT_SIZE_LARGE = byteArrayOf(0x1D, 0x21, 0x11) // 가로세로 2배

    // 용지 배출 및 커팅
    val FEED_PAPER = byteArrayOf(0x1B, 0x64, 0x03) // 3줄 피드
    val PAPER_CUT = byteArrayOf(0x1D, 0x56, 0x42, 0x00) // Full Cutval RESET = byteArrayOf()
}