package com.example.ykiosk_android_test.DTO.response

import com.google.gson.annotations.SerializedName

data class StoreResponse (
    @SerializedName("storeName")
    var storeName : String,
    @SerializedName("storeId")
    var storeId : String,
    @SerializedName("state")
    var state : String,
)