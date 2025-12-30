package com.example.ykiosk_android_test.Item

import com.example.ykiosk_android_test.DTO.response.MenuDetailResponse
import com.example.ykiosk_android_test.DTO.response.OptionCategoryDetailResponse

data class CartItem(
    val menu: MenuDetailResponse,
    val quantity: Int,
    val selectedOptions: Map<Int, List<OptionCategoryDetailResponse>> // 5층 ID - 6층 객체들
)