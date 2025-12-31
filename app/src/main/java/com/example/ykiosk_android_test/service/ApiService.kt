package com.example.ykiosk_android_test.service

import com.example.ykiosk_android_test.DTO.request.OrderRequest
import com.example.ykiosk_android_test.DTO.response.StoreMenuDetailResponse
import com.example.ykiosk_android_test.DTO.response.StoreResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("api/user/store/list")
    fun getMyStoreList(): Call<List<StoreResponse>>

    //대충 post용은 이렇게 생김
    @POST("api 주소")
    fun postmethod(): Call<Int>

    @GET("api/user/store/menu-list")
    suspend fun getStoreMenuList(
        @Query("storeId") storeId : String
    ): Response<StoreMenuDetailResponse>

    @POST("api/user/store/menu/new-order")
    suspend fun postOrder(
        @Body orderRequest: OrderRequest // 우리가 만든 DTO 객체
    ): Response<Int> // 성공/실패 응답 (Unit은 내용물이 없을 때 사용)
}