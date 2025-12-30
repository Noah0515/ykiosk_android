package com.example.ykiosk_android_test.view_model

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ykiosk_android_test.DTO.response.StoreResponse
import com.example.ykiosk_android_test.auth.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreViewModel(context: Context) : ViewModel() {
    private val apiService = RetrofitClient.getApiService(context)

    var stores by mutableStateOf<List<StoreResponse>>(emptyList())
        private set

    init {
        fetchStores()
    }


    fun fetchStores() {
        Log.d("API_CHECK", "fetchStores() 함수 시작됨")

        apiService.getMyStoreList().enqueue(object : Callback<List<StoreResponse>> {
            override fun onResponse(call: Call<List<StoreResponse>>, response: Response<List<StoreResponse>>) {
                if (response.isSuccessful) {
                    val fetchedData = response.body()

                    stores = fetchedData ?: emptyList()
                    Log.d("API_SUCCESS", "가게 개수: ${stores?.size}")
                } else if (response.code() == 401) {
                    Log.e("API_ERROR", "토큰이 만료되었거나 권한이 없습니다.")
                    // 여기서 로그인 화면으로 튕기게 하는 로직을 넣을 수 있습니다.
                }
            }

            override fun onFailure(call: Call<List<StoreResponse>>, t: Throwable) {
                Log.e("API_FAILURE", "서버 통신 실패: ${t.message}")
            }
        })
    }
}