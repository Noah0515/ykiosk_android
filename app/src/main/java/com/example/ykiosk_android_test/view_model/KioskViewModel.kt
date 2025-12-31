package com.example.ykiosk_android_test.view_model

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ykiosk_android_test.DTO.request.OrderRequest
import com.example.ykiosk_android_test.DTO.request.OrderedMenuDto
import com.example.ykiosk_android_test.DTO.request.OrderedMenuOptionDto
import com.example.ykiosk_android_test.DTO.response.MenuGroupDetailResponse
import com.example.ykiosk_android_test.DTO.response.StoreMenuDetailResponse
import com.example.ykiosk_android_test.DTO.response.StoreResponse
import com.example.ykiosk_android_test.Item.CartItem
import com.example.ykiosk_android_test.auth.RetrofitClient
import com.example.ykiosk_android_test.bluetooth.BluetoothPrinterManager
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KioskViewModel(
    storeId: String,
    context: Context
) : ViewModel() {
    private val apiService = RetrofitClient.getApiService(context)

    var storeMenuDetail by mutableStateOf<StoreMenuDetailResponse?>(null)
        private set
    var isLoading by mutableStateOf(false)
        private set

    var selectedGroup by mutableStateOf<MenuGroupDetailResponse?>(null)

    val cartList = mutableStateListOf<CartItem>()

    var isOrdering by mutableStateOf(false)
        private set

    fun addToCart(newItem: CartItem) {
        // 1. 기존 장바구니에 '메뉴ID'와 '선택한 옵션들'이 완전히 일치하는 항목이 있는지 확인
        val existingItem = cartList.find {
            it.menu.menuId == newItem.menu.menuId && it.selectedOptions == newItem.selectedOptions
        }

        if (existingItem != null) {
            // 2. 이미 있다면 해당 항목의 인덱스를 찾아 수량만 증가시킨 새 객체로 교체
            val index = cartList.indexOf(existingItem)
            cartList[index] = existingItem.copy(quantity = existingItem.quantity + newItem.quantity)
        } else {
            // 3. 없다면 새롭게 추가 (담은 순서대로 아래에 쌓임)
            cartList.add(newItem)
        }
    }

    fun updateQuantity(item: CartItem, delta: Int) {
        val index = cartList.indexOf(item)
        if (index == -1) return

        val newQuantity = item.quantity + delta
        if (newQuantity <= 0) {
            // 수량이 0 이하가 되면 삭제
            cartList.removeAt(index)
        } else {
            // 수량 수정
            cartList[index] = item.copy(quantity = newQuantity)
        }
    }

    init {
        fetchStoreMenuDetail(storeId)
    }

    fun selectGroup(group: MenuGroupDetailResponse) {
        selectedGroup = group
    }
    fun fetchStoreMenuDetail(storeId: String) {
        // 비동기 처리를 위해 viewModelScope 사용
        viewModelScope.launch {
            isLoading = true
            Log.d("API_CHECK", "가게 ID: $storeId - 메뉴 불러오기 시작")

            try {
                val response = apiService.getStoreMenuList(storeId)

                if (response.isSuccessful) {
                    // 성공적으로 데이터를 가져온 경우
                    storeMenuDetail = response.body()
                    val jsonString = Gson().toJson(storeMenuDetail)
                    logLongString("API_CHECK", jsonString)
                    selectedGroup = storeMenuDetail?.menuGroupDetailResDtoList?.firstOrNull()
                //Log.d("API_CHECK", "성공: ${storeMenuDetail?.toString()}")
                } else {
                    // 서버 에러 발생 (404, 500 등)
                    Log.e("API_CHECK", "서버 에러: ${response.code()}")
                }
            } catch (e: Exception) {
                // 네트워크 연결 오류 등 예외 상황
                Log.e("API_CHECK", "네트워크 오류: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    fun logLongString(tag: String, message: String) {
        val maxLogSize = 3000
        for (i in 0..message.length / maxLogSize) {
            val start = i * maxLogSize
            var end = (i + 1) * maxLogSize
            end = if (end > message.length) message.length else end
            Log.d(tag, message.substring(start, end))
        }
    }

    fun submitOrder(
        context: Context,
        storeId: String,
        deviceAddress: String,
        onSuccess: (Int) -> Unit,
        onError: (String) -> Unit) {
        if (cartList.isEmpty()) return

        viewModelScope.launch {
            isOrdering = true
            try {
                val request = OrderRequest(
                    storeId = storeId,
                    orderedMenus = cartList.map { cartItem ->
                        OrderedMenuDto(
                            menuId = cartItem.menu.menuId,
                            quantity = cartItem.quantity,
                            orderedMenuOptions = cartItem.selectedOptions.flatMap { (optionId, categories) ->
                                categories.map { category ->
                                    OrderedMenuOptionDto(
                                        optionId = optionId,
                                        categoryId = category.categoryId,
                                        optionContent = category.optionContent
                                    )
                                }
                            }
                        )
                    }
                )

                // 2. 💡 구축하신 RetrofitClient 사용
                val apiService = RetrofitClient.getApiService(context)
                val response = apiService.postOrder(request)

                if (response.isSuccessful) {
                    val orderNum = response.body() ?: -1
                    printReceipt(orderNum, deviceAddress)
                    cartList.clear()
                    onSuccess(orderNum)
                    Log.e("주문번호: ", response.body().toString())
                } else {
                    // 토큰 만료(401)나 서버 에러 처리
                    onError("주문 실패: ${response.code()}")
                }
            } catch (e: Exception) {
                onError("네트워크 연결을 확인해주세요: ${e.message}")
            } finally {
                isOrdering = false
            }
        }

    }
    private suspend fun printReceipt(orderNum: Int, deviceAddress: String) {
        val printerManager = BluetoothPrinterManager(BluetoothAdapter.getDefaultAdapter())
        Log.e("printReceipt ", "메소드 진입")
        // 비동기로 연결 및 출력 시도
        withContext(Dispatchers.IO) {
            if (printerManager.connect(deviceAddress)) {
                printerManager.printActualReceipt(
                    orderNum = orderNum,
                    cartList = cartList.toList(), // 현재 장바구니 복사본
                    storeName = "가게1" // 필요시 매장명 전달
                )
                Log.e("printReceipt ", "출력")
                printerManager.closeConnection()
            }
        }
    }
}