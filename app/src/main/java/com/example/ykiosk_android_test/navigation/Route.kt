package com.example.ykiosk_android_test.navigation

object NavRoute {
    //파라미터 없는 경로
    const val START = "start_screen" // 시작 화면
    const val ORDER = "order_screen" // 주문 화면
    const val PRINTER_LIST = "printer_list_screen" //프린터 목록 화면
    //파라미터 있는 경로
    const val ORDER_COMPLETE = "order_complete_screen/{storeId}?data={orderData}" // 가게id와 주문정보(json)을 보냄

    fun orderCompletePath(storeId : String, orderData : String) : String {// json으로 전달. 근데 json은 못가서 string으로 보내고 parsing
        return "order_complete_screen/$storeId?data=$orderData"
    }

}