package com.example.ykiosk_android_test.order

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ykiosk_android_test.custom_widget.button.CategoryButton
import com.example.ykiosk_android_test.custom_widget.button.GroupButton
import com.example.ykiosk_android_test.custom_widget.customBorder
import com.example.ykiosk_android_test.custom_widget.text.LargeText1
import com.example.ykiosk_android_test.custom_widget.text.LargeText2
import com.example.ykiosk_android_test.custom_widget.text.LargeText3
import com.example.ykiosk_android_test.custom_widget.text.LargeText4
import com.example.ykiosk_android_test.custom_widget.text.MediumText3
import com.example.ykiosk_android_test.custom_widget.text.MediumText4

import com.example.ykiosk_android_test.ui.theme.GrayLight
import com.example.ykiosk_android_test.ui.theme.Ykiosk_android_testTheme
import com.example.ykiosk_android_test.ui.theme.*

@Composable
fun OrderScreen(onNavigateToOrderComplete : () -> Unit, str : String = "식당 이름", modifier: Modifier = Modifier){
    Log.d("order", "OrderScreen")
    BoxWithConstraints (// 화면의 사이즈를 알기 위해 필요한 가장 바깥 껍데기
        modifier = Modifier
            .background(Gray)
            .fillMaxSize()
    ) {

        val paramWidth = this.maxWidth
        val paramHeight = this.maxHeight

        val horizontalPadding = paramWidth * 0.05f
        val verticalPadding = paramHeight * 0.05f
        Surface (// 실질적인 바깥 껍데기
            color = GraySemiLight,
            modifier = Modifier
                .background(DefaultBackgroundGray)
                .padding(8.dp)
                .fillMaxSize(),
            shape = RoundedCornerShape(16.dp)
        ){
            Row (// 세로 레이아웃들 싸는거
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Box(// 메뉴 카테고리
                    modifier = Modifier
                        .weight(3f)
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color(0x00000000),
                                shape = RoundedCornerShape(topStart = 10.dp, topEnd = 0.dp, bottomStart = 10.dp, bottomEnd = 0.dp))

                    ) {
                        Box(
                            modifier = Modifier
                                .weight(3f)
                                .padding(5.dp, 5.dp, 0.dp, 0.dp)
                                .background(Gray,
                                    RoundedCornerShape(topStart = 10.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 0.dp))
                                .fillMaxSize()
                                .customBorder(8.dp, bottom = true)
                            ,
                            contentAlignment = Alignment.Center
                        ) {
                            LargeText3(
                                "메뉴 카테고리"
                            )
                        }

                        Column(
                            modifier = Modifier
                                .weight(17f)
                                .padding(5.dp, 0.dp, 0.dp, 5.dp)
                                .background(Gray)
                                .fillMaxSize()
                        ) {
                            CategoryButton(
                                "음식",
                                modifier = Modifier
                                    .padding(5.dp,4.dp,)
                            ) {}

                            CategoryButton(
                                "음료",
                                modifier = Modifier
                                    .padding(5.dp,4.dp,)
                            ) {}
                        }
                    }

                }

                Box(// 메뉴 항목
                    modifier = Modifier
                        .weight(12f)
                        .background(MenuAreaBackgroundWhite)
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(3f)
                                .fillMaxSize()
                                .padding(5.dp, 5.dp, 0.dp, 0.dp)
                                .customBorder(borderWidth = 8.dp, bottom = true)
                        ) {

                        }

                        Box(
                            modifier = Modifier
                                .weight(17f)
                                .padding(5.dp)
                                .background(MenuAreaBackgroundWhite)
                                .fillMaxSize()
                        ) {
                            Column (

                            ) {
                                Box(
                                    modifier = Modifier
                                        .weight(2f)
                                        .fillMaxSize()
                                        .padding(10.dp, 0.dp)
                                        .customBorder(borderWidth = 8.dp, borderColor = Dark3, bottom = true)
                                ) {
                                    Row(

                                    ) {
                                        GroupButton(
                                            text = "커피",
                                            modifier = Modifier
                                                .padding(5.dp, 0.dp, 5.dp, 2.dp)
                                        ) { }

                                        GroupButton(
                                            text = "차",
                                            modifier = Modifier
                                                .padding(5.dp, 0.dp)
                                        ) { }
                                    }

                                }

                                Box(
                                    modifier = Modifier
                                        .weight(18f)
                                        .fillMaxSize()
                                        .padding(10.dp)
                                ) {

                                }
                            }
                        }
                    }
                }

                Box(// 담은 메뉴
                    modifier = Modifier
                        .weight(5f)
                        .background(Color.Green)
                        .padding(5.dp)
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(3f)
                                .background(ChoicedBackground)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            LargeText2(
                                "담은 메뉴"
                            )
                        }

                        Box(
                            modifier = Modifier
                                .weight(17f)
                                .background(ChoicedBackground)
                                .fillMaxSize()
                        ) {

                        }
                    }
                }
            }


        }
    }

}

@Composable
@Preview(
    showBackground = true,
    device = Devices.TABLET,
    widthDp = 1794,
    heightDp = 1120
)
fun OrderScreenPrev() {
    Ykiosk_android_testTheme {
        OrderScreen(onNavigateToOrderComplete = {})
    }
}