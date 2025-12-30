package com.example.ykiosk_android_test.service

import com.example.ykiosk_android_test.DTO.response.StoreMenuDetailResponse
import com.example.ykiosk_android_test.DTO.response.StoreResponse
import retrofit2.Call
import retrofit2.Response
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
}