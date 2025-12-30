package com.example.ykiosk_android_test.service

import com.example.ykiosk_android_test.DTO.response.StoreResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("api/user/store/list")
    fun getMyStoreList(): Call<List<StoreResponse>>

    //대충 post용은 이렇게 생김
    @POST("api 주소")
    fun postmethod(): Call<Int>
}