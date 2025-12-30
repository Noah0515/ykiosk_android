package com.example.ykiosk_android_test.DTO.response

import kotlinx.serialization.Serializable

@Serializable
data class StoreMenuDetailResponse(
    val storeName: String,
    val storeId: String,
    val menuGroupDetailResDtoList: List<MenuGroupDetailResponse>
)

// 2층: 메뉴 그룹
data class MenuGroupDetailResponse(
    val menuGroupName: String,
    val menuGroupId: Int,
    val menuCategoryDetailResDtoList: List<MenuCategoryDetailResponse>
)

// 3층: 메뉴 카테고리
data class MenuCategoryDetailResponse(
    val menuCategoryId: Int,
    val menuCategoryName: String,
    val menuDetailResDtoList: List<MenuDetailResponse>
)

// 4층: 메뉴 상세 (이미지 URL 포함)
data class MenuDetailResponse(
    val menuId: Int,
    val menuName: String,
    val menuInfo: String?,
    val allergy: String?,
    val menuState: String, // Enum은 String으로 받거나 코틀린 Enum으로 매핑 가능
    val imageUrl: String?,
    val menuOptionDetailResDtoList: List<MenuOptionDetailResponse>
)

// 5층: 메뉴 옵션
data class MenuOptionDetailResponse(
    val optionName: String,
    val optionId: Int,
    val selectionNum: Int,
    val optionCategoryDetailResDtoList: List<OptionCategoryDetailResponse>
)

// 6층: 옵션 카테고리(상세 내용)
data class OptionCategoryDetailResponse(
    val categoryId: Int,
    val optionContent: String
)
