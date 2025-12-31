package com.example.ykiosk_android_test.DTO.request

// 1. 주문 전체
data class OrderRequest(
    val storeId: String,
    val orderedMenus: List<OrderedMenuDto>
)

// 2. 주문된 메뉴
data class OrderedMenuDto(
    val menuId: Int,
    val quantity: Int,
    val orderedMenuOptions: List<OrderedMenuOptionDto>
)

// 3. 선택된 옵션
data class OrderedMenuOptionDto(
    val optionId: Int,
    val categoryId: Int,
    val optionContent: String
)
